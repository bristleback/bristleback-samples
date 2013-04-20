var config = {
//  serverUrl: "ws://samples.bristleback.pl/scrumtable/websocket",
  serverUrl: (document.location.hostname == "samples.bristleback.pl") ? "ws://samples.bristleback.pl/scrumtable/websocket" : "ws://localhost:8080/websocket",

  OnOpen: function (event) {
    switchToConnectedScreen();
    widgetActionClass.userLogged(Bristleback.Connector);
  },
  OnClose: function (event) {
    switchDisconectedScreen();
  }
};
var client = Bristleback.newClient(config);
var dataController = client.dataController;

<!--CLIENT ACTIONS-->
var widgetActionClass = dataController.getActionClass("Widget");

widgetActionClass.defineAction("addWidget");
widgetActionClass.defineAction("moveWidget");
widgetActionClass.defineAction("lockWidget");
widgetActionClass.defineAction("unlockWidget");
widgetActionClass.defineAction("editWidget");
widgetActionClass.defineAction("resizeWidget");
widgetActionClass.defineAction("clearWidgets");
widgetActionClass.defineAction("userLogged");

<!--SERVER EVENTS HANDLING-->
var widgetClientActionClass = {
  newWidget: function (widget) {
    var id = widget.id;
    appendNewNote(id);
    setWidthHeightPosition(widget);
    setLeftTopPosition(widget);
    addResizeAndDragSupport("#" + id);
    setWidgetValues(widget);
  },
  editWidget: function (widget) {
    setWidgetValues(widget);
  },
  widgetsList: function (widgets) {
    for (var widgetIndex in widgets) {
      var widget = widgets[widgetIndex];
      appendNewNote(widget.id);
      setLeftTopPosition(widget);
      setWidthHeightPosition(widget);
      addResizeAndDragSupport("#" + widget.id);
      setWidgetValues(widget);
      if (widget.locked == true) {
        var widgetElement = $("#" + widget.id);
        widgetElement.resizable("option", "disabled", true);
        widgetElement.draggable("option", "disabled", true);
      }
    }
  },
  lockWidget: function (widget) {
    var widgetElement = $("#" + widget.id);
    widgetElement.resizable("option", "disabled", true);
    widgetElement.draggable("option", "disabled", true);
    widgetElement.addClass("widgetLocked");
  },
  unlockWidget: function (widget) {
    var widgetElement = $("#" + widget.id);
    widgetElement.resizable("option", "disabled", false);
    widgetElement.draggable("option", "disabled", false);
    widgetElement.removeClass("widgetLocked");
  },
  resizeWidget: function (widget) {
    setWidthHeightPosition(widget);
  },
  moveWidget: function (widget) {
    setLeftTopPosition(widget);
  },

  clearWidgets: function () {
    $(".note").remove();
  }
};

dataController.registerClientActionClass("WidgetClientAction", widgetClientActionClass);
<!-- END SERVER EVENTS-->

function connect() {
  client.connect();
  switchToConnectingScreen();
}


function requestChangePosition(ui) {
  var widget = {
    id: ui.helper.context.id,
    position: {
      top: ui.position.top,
      left: ui.position.left
    }
  };
  widgetActionClass.moveWidget(widget);
}

function requestChangeSize(ui) {
  var widget = {
    id: ui.originalElement.context.id,
    position: {
      height: parseInt(ui.size.height),
      width: parseInt(ui.size.width)
    }
  };
  widgetActionClass.resizeWidget(widget);
}

function requestLockWidget(id) {
  var widget = {
    id: id
  };
  widgetActionClass.lockWidget(widget);
}

function requestUnlockWidget(id) {
  var widget = {
    id: id
  };
  widgetActionClass.unlockWidget(widget);
}

function requestEditWidget(widget) {
  widgetActionClass.editWidget(widget);
}


function addEditNoteListeners(id) {
  var currentId = id;
  $("#" + id + " .edit-button").click(function (e) {
    var widget = $("#" + currentId);
    if (!widget.hasClass("widgetLocked")) {
      widget.find(".switcher div").toggle();
      $(this).hide();
      $(this).next().show();
      requestLockWidget(currentId);
      widget.addClass("edit-mode");
    }
  });

  $("#" + id + " .save-button").click(function (e) {
    var widget = $("#" + currentId);
    widget.find(".switcher div").toggle();
    widget.removeClass("edit-mode");

    var titleInput = $(this).parent().parent().find(".input-title");
    var titleValue = titleInput.val();

    var descriptionInput = $(this).parent().parent().find(".input-description");
    var descriptionValue = descriptionInput.val();

    var ownerInput = $(this).parent().parent().find(".input-owner");
    var ownerValue = ownerInput.val();

    var timeInput = $(this).parent().parent().find(".input-time");
    var timeValue = timeInput.val();

    $(this).hide();
    $(this).prev().show();
    requestEditWidget({
      id: id,
      title: titleValue,
      description: descriptionValue,
      owner: ownerValue,
      time: timeValue
    });
    requestUnlockWidget(currentId);
  });
}


<!--JQUERY scripits-->
function addResizeAndDragSupport(elementSelector) {
  $(elementSelector).resizable({
    maxHeight: 550,
    maxWidth: 550,
    minHeight: 155,
    minWidth: 125,
    alwaysRelative: true,
    stop: function (event, ui) {
      //do nothing for now
    },
    resize: function (event, ui) {
      requestChangeSize(ui);
    }
  });

  $(elementSelector).draggable({
    containment: "#outer",
    snap: true,
    alwaysRelative: true,
    distance: 30,
    handle: ".ui-widget-header",
    start: function (event, ui) {
      //do nothing for now
    },
    drag: function (event, ui) {
      requestChangePosition(ui);

    },
    stop: function (event, ui) {
      //do nothing for now
    }
  });
}

function appendNewNote(id) {
  var newNote = "<div id=\"" + id + "\" class=\"note draggable ui-widget-content\">\n  <div class=\"ui-widget-header switcher\">\n    <div class=\"title-label\" id=\"" + id + "-title-value\">New</div>\n    <div id=\"" + id + "-title-input\" class=\"hidden\">\n      <input class=\"input-title\" type=\"text\" value=\"New\"/>\n    </div>\n  </div>\n  <div class=\"switcher\">\n    <div class='description-value' id=\"" + id + "-description-value\">Description</div>\n    <div id=\"" + id + "-description-input\" class=\"hidden\">\n      <input class=\"input-description\" type=\"text\" value=\"Description\"/>\n    </div>\n  </div>\n  <div class=\"noteLabel\">Owner:</div>\n  <div class=\"switcher\">\n    <div id=\"" + id + "-owner-value\">Owner</div>\n    <div id=\"" + id + "-owner-input\" class=\"hidden\">\n      <input class=\"input-owner\" type=\"text\" value=\"Owner\"/>\n    </div>\n  </div>\n  <div class=\"noteLabel\">Time:</div>\n  <div class=\"switcher\">\n    <div id=\"" + id + "-time-value\">5h</div>\n    <div id=\"" + id + "-time-input\" class=\"hidden\">\n      <input class=\"input-time\" type=\"text\" value=\"5h\"/>\n    </div>\n  </div>\n  <ul id=\"icons\" style=\"float: right;\">\n        <li class=\"ui-state-default ui-corner-all edit-button\"><span class=\"ui-icon ui-icon-refresh\"></span></li>\n        <li class=\"ui-state-default ui-corner-all save-button\"><span class=\"ui-icon ui-icon-refresh\"></span></li>\n  </ul>\n</div>\n";
  $("#outer").append(newNote);
  addEditNoteListeners(id);
}

function setWidgetValues(widget) {
  var id = widget.id;
  $("#" + id + "-title-value").text(widget.title);
  $("#" + id + "-description-value").text(widget.description);
  $("#" + id + "-owner-value").text(widget.owner);
  $("#" + id + "-time-value").text(widget.time);

  $("#" + id + "-title-input input").val(widget.title);
  $("#" + id + "-description-input input").val(widget.description);
  $("#" + id + "-owner-input input").val(widget.owner);
  $("#" + id + "-time-input input").val(widget.time);
}


function setLeftTopPosition(params) {
  var leftPos = parseInt(params.position.left);
  var topPos = parseInt(params.position.top);
  var widgetElement = $("#" + params.id);
  widgetElement.css("left", leftPos);
  widgetElement.css("top", topPos);
}
function setWidthHeightPosition(params) {
  var height = params.position.height;
  var width = params.position.width;
  var widgetElement = $("#" + params.id);
  widgetElement.css("height", height);
  widgetElement.css("width", width);
}


function switchDisconectedScreen() {
  setStatus("disconnected", "status-busy");
}

function switchToConnectingScreen() {
  setStatus("connecting", "status-away")
}

function switchToConnectedScreen() {
  setStatus("connected", "status");
}

function setStatus(statusDescription, iconName) {
  $('#connectionStatus').text(statusDescription);
  $('#statusIcon').attr("src", "img/" + iconName + ".png");
}

function createJqueryButtons() {
  $(".fancyButton")
    .button()
    .click(function (event) {
      event.preventDefault();
    });
}

$(document).ready(function () {
  connect();
  addResizeAndDragSupport(".note");

  $("#addNoteButton").click(function () {
    widgetActionClass.addWidget();
  });

  $("#removeNotes").click(function () {
    widgetActionClass.clearWidgets();
  });

  createJqueryButtons();
});


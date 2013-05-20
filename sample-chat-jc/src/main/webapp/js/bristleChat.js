var Sample = {};

function prepareClient() {
  var config = {
    serverUrl: (document.location.hostname == "samples.bristleback.pl") ? "ws://samples.bristleback.pl/chat/websocket" : "ws://localhost:8080/websocket",
    onOpen: function (event) {
      switchToLoggingScreen();
      Sample.joinChatActionClass.executeDefault(Bristleback.USER_CONTEXT, Sample.username);
    },
    onClose: function (event) {
      switchToLoginScreen();
    }
  };
  Sample.client = Bristleback.newClient(config);
  Sample.dataController = Sample.client.dataController;
}


/*----------------- ACTION CLASSES*/

function prepareActionClasses() {
  defineJoinChatActionClass();
  defineSendTextActionClass();
}

function defineJoinChatActionClass() {
  Sample.joinChatActionClass = Sample.dataController.getActionClass("JoinChat");
  Sample.joinChatActionClass.defineDefaultAction().setResponseHandler(onLogInCallback)
    .exceptionHandler
    .setExceptionHandler("DeserializationException", validationErrorCallback)
    .setExceptionHandler("UserExistsException", userExistsErrorCallback);

  function onLogInCallback(users) {
    switchToLoggedScreen();

    var messages = $("#messages");
    scrollDownChat();
    $("#speakChannel").focus();
    actualUsersList(users)
  }

  function validationErrorCallback() {
    switchToLoginScreen();
    $("#loginErrors").html("Username required");
  }

  function userExistsErrorCallback() {
    switchToLoginScreen();
    $("#loginErrors").html("User already exists, choose other nickname");
  }
}

function defineSendTextActionClass() {
  Sample.sendTextActionClass = Sample.dataController.getActionClass("SendText");
  Sample.sendTextActionClass.defineDefaultAction();
}

/*----------------- CLIENT ACTION CLASSES*/

function prepareClientActionClass() {
  var chatClientActionClass = {
    newUser: function (userName, actualList) {
      addMessage(userName + " has joined chat");
      actualUsersList(actualList);
    },

    sendText: function (user, chatText) {
      var userOnList = $("#" + user.id);
      if (chatText.finished) {
        addMessage(Bristleback.utils.escapeHTML(user.nickname) + ":", Bristleback.utils.escapeHTML(chatText.text));
        userOnList.empty();
      } else {
        userOnList.empty();
        if (chatText.text !== "") {
          userOnList.append("<table><tr><td><img src=\"img/ajax-loader.gif\"></td><td>" +
            Bristleback.utils.escapeHTML(chatText.text) + "</td></tr></table>");
        }
      }
    },

    userLeftChat: function (userName, actualList) {
      addMessage(userName + " has left chat");
      actualUsersList(actualList);
    }
  };

  Sample.dataController.registerClientActionClass("ChatClientAction", chatClientActionClass);

}

/* ---------------- GUI*/

function addGUIListeners() {
  $("#loginButton").click(function () {
    Sample.username = $("#login").val();
    if (Sample.client.isConnected()) {
      switchToLoggingScreen();
      Sample.joinChatActionClass.executeDefault(Bristleback.USER_CONTEXT, Sample.username);
    } else {
      Sample.client.connect();
      switchToConnectingScreen();
    }
  });

  $("#speakChannel").keyup(function (e) {
    if (e.keyCode == 13) {
      sendText(true);
    } else {
      sendText(false);
    }
  });

  function sendText(finished) {
    var textInput = $("#speakChannel");
    var textMessage = {
      text: textInput.val(),
      finished: finished
    };
    Sample.sendTextActionClass.executeDefault(Bristleback.USER_CONTEXT, textMessage);
    if (finished) {
      textInput.val("");
    }
  }
}

function addMessage(boldText, normalText) {
  normalText = normalText || "";
  $("#messages").append("<li><span>" + boldText + " </span>" + normalText + "</li>");
  scrollDownChat();
}

function actualUsersList(users) {
  var usersList = $(".users");
  usersList.empty();
  for (var user in users) {
    var backgroundClass = user % 2 == 0 ? "evenUser" : "oddUser";
    usersList.append("<li class='" + backgroundClass + "'>" + Bristleback.utils.escapeHTML(users[user].nickname) + "<div class=\"instantMsg\" id=\"" + users[user].id + "\"></div></li>")

  }
}

function scrollDownChat() {
  var chatFrame = $("#chatFrame");
  chatFrame.scrollTop(chatFrame[0].scrollHeight);
}

function switchToLoginScreen() {
  $('#afterLogInPane').hide();
  $('#loggingPage').hide();
  $('#loginPage').show();
  setStatus("disconnected", "status-busy");
}

function switchToConnectingScreen() {
  $('#loginPage').hide();
  $('#afterLogInPane').hide();
  $('#loggingPage').show();
  setStatus("connecting", "status-away")
}

function switchToLoggingScreen() {
  $("messages").text("");
  $('#loginPage').hide();
  $('#afterLogInPane').hide();
  $('#loggingPage').show();
  setStatus("logging in", "status-away");
}

function switchToLoggedScreen() {
  $("#messages").text("");
  $('#loginPage').hide();
  $('#loggingPage').hide();
  $('#afterLogInPane').show();
  setStatus("logged in", "status");
}

function setStatus(statusDescription, iconName) {
  $('#connectionStatus').text(statusDescription);
  $('#statusIcon').attr("src", "img/" + iconName + ".png");
}

/* ---------------- WIRING ALL TOGETHER*/

$(document).ready(function () {
  addGUIListeners();
  prepareClient();
  prepareActionClasses();
  prepareClientActionClass();
});
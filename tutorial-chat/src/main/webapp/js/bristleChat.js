    /* main container for our chat application */
    var Chat = {};

    function prepareClient() {
      var config = {
        /* url of backend server, 8765 is default port for standalone app, can be changed in server configuration */
        serverUrl: "ws://localhost:8765/websocket",
        /* function invoked after establishing connection */
        OnOpen: function (event) {
          /**
           *   Bristleback.CONNECTOR - alias for current user used in java script client
           *   Chat.username - variable will hold name of logged user
           */
          Chat.joinChatActionClass.executeDefault(Bristleback.CONNECTOR, Chat.username);
        },
        /* function invoked when connection is closed*/
        OnClose: function (event) {
          alert("disconnected");
          Chat.joinChatActionClass.executeDefault(Bristleback.CONNECTOR, Chat.username);
        }
      };
      /*new bristleback client created*/
      Chat.client = Bristleback.newClient(config);
      /* for simplicity we assign data bristleback data controller to our chat object*/
      Chat.dataController = Chat.client.dataController;
    }


/*----------------- ACTION CLASSES*/

function prepareActionClasses() {
  defineJoinChatActionClass();
  defineSendTextActionClass();
}

function defineJoinChatActionClass() {
  Chat.joinChatActionClass = Chat.dataController.getActionClass("JoinChat");
  Chat.joinChatActionClass.defineDefaultAction().setResponseHandler(onLogInCallback)
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
  Chat.sendTextActionClass = Chat.dataController.getActionClass("SendText");
  Chat.sendTextActionClass.defineDefaultAction();
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

  Chat.dataController.registerClientActionClass("ChatClientAction", chatClientActionClass);

}

/* ---------------- GUI*/

function addGUIListeners() {
  $("#loginButton").click(function () {
    Chat.username = $("#login").val();
    if (Chat.client.isConnected()) {
      switchToLoggingScreen();
      Chat.joinChatActionClass.executeDefault(Bristleback.CONNECTOR, Chat.username);
    } else {
      Chat.client.connect();
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
    Chat.sendTextActionClass.executeDefault(Bristleback.CONNECTOR, textMessage);
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
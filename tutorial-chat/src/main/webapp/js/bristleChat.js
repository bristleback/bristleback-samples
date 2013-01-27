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
  /**
   * we're defining new action class - JoinChat - same name needs to be used in Java action class
   * we also define response handler for default action - onLogInCallback function
   * and two handlers fro exceptions
   */
  Chat.joinChatActionClass = Chat.dataController.getActionClass("JoinChat");
  Chat.joinChatActionClass.defineDefaultAction().setResponseHandler(onLogInCallback)
    .exceptionHandler
    .setExceptionHandler("DeserializationException", validationErrorCallback)
    .setExceptionHandler("UserExistsException", userExistsErrorCallback);

  function onLogInCallback(users) {
    switchToLoggedScreen(); //show messages + users screeen
    scrollDownChat();  //to display most recent message
    $("#speakChannel").focus(); //to position cursor in input area
    actualUsersList(users); //create list with logged users
  }

  //callback for exceptionHandler
  function validationErrorCallback() {
    alert("Username required");
  }

  //callback for exceptionHandler
  function userExistsErrorCallback() {
    alert("User already exists, choose other nickname");
  }
}

/**
 * we're defining new action class - SendText - same name needs to be used in Java action class
 * This time we're only initializing action class by creating default action (executeDefault method in Java action class)
 * Action will be used only for sending message to server with no response, so we don't need to specify response handler
 */
function defineSendTextActionClass() {
  Chat.sendTextActionClass = Chat.dataController.getActionClass("SendText");
  Chat.sendTextActionClass.defineDefaultAction();
}

/*----------------- CLIENT ACTION CLASSES*/

function prepareClientActionClass() {
  /**
   * Each method of below object will handle different client action (event from server)
   * names of method needs to be same as method names in Java client
   */
  var chatClientActionClass = {
    //new user has joined the chat
    newUser: function (userName, actualList) {
      addMessage(userName + " has joined chat"); //we're displaying info message with username
      actualUsersList(actualList); //we need to update user list with new content (as argument we've go list of all users)
    },

    //new message was sent, we need to add it to messages list
    sendText: function (user, chatText) {
      addMessage(Bristleback.utils.escapeHTML(user.nickname) + ":", Bristleback.utils.escapeHTML(chatText.text));
    },

    userLeftChat: function (userName, actualList) {
      addMessage(userName + " has left chat"); //we're displaying info message with username
      actualUsersList(actualList); //we need to update user list with new content (as argument we've go list of all users)
    }
  };
  // new client action needs to be registered in action controller
  Chat.dataController.registerClientActionClass("ChatClientAction", chatClientActionClass);
}

/* ---------------- GUI*/

function addGUIListeners() {
  $("#loginButton").click(function () {
    Chat.username = $("#login").val();
    Chat.client.connect();
  });
}

function addMessage(boldText, normalText) {
  // ?
  Chat.sendTextActionClass.executeDefault(Bristleback.CONNECTOR, boldText);

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


function switchToLoggedScreen() {
  $("#messages").text("");
  $('#loginPage').hide();
  $('#loggingPage').hide();
  $('#afterLogInPane').show();
}

/* ---------------- WIRING ALL TOGETHER*/

$(document).ready(function () {
  addGUIListeners();
  prepareClient();
  prepareActionClasses();
  prepareClientActionClass();
});
var Sample = {};

function prepareClient() {
  var config = {
    serverUrl: (document.location.hostname == "samples.bristleback.pl") ? "ws://samples.bristleback.pl/chat/websocket" : "ws://localhost:8080/websocket",

    OnOpen: function (event) {
      console.log("connected");
      switchToConnectedScreen();
    },
    OnClose: function (event) {
      switchDisconectedScreen();
    }
  };
  Sample.client = Bristleback.newClient(config);
  Sample.dataController = Sample.client.dataController;
}


/*----------------- ACTION CLASSES*/
function prepareActionClasses() {
}

/*----------------- CLIENT ACTION CLASSES*/
function prepareClientActionClass() {
  var displayGraphAction = {
    graphData : function(x, y , z) {
        graphData.line1.append(new Date().getTime(), x);
        graphData.line2.append(new Date().getTime(), y);
        graphData.line3.append(new Date().getTime(), z);
    }
  };
  Sample.dataController.registerClientActionClass("DisplayGraphAction", displayGraphAction);

}

/* ---------------- GUI*/
function switchToConnectingScreen() {
  setStatus("connecting", "status-away")
}
function switchToConnectedScreen() {
  setStatus("connected", "status");
}
function switchDisconectedScreen() {
  setStatus("disconnected", "status-busy");
}


function setStatus(statusDescription, iconName) {
  $('#connectionStatus').text(statusDescription);
  $('#statusIcon').attr("src", "img/" + iconName + ".png");
}

/* ---------------- WIRING ALL TOGETHER*/
var graphData = {};
$(document).ready(function () {
  prepareClient();
  prepareActionClasses();
  prepareClientActionClass();

  Sample.client.connect();
  switchToConnectingScreen();


  var smoothie = new SmoothieChart();
  smoothie.streamTo(document.getElementById("mycanvas"));

  // Data
  graphData.line1 = new TimeSeries();
  graphData.line2 = new TimeSeries();
  graphData.line3 = new TimeSeries();


// Add to SmoothieChart
  smoothie.addTimeSeries(graphData.line1);
  smoothie.addTimeSeries(graphData.line2);
  smoothie.addTimeSeries(graphData.line3);
});
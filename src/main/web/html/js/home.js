  function resizeIframe() {
      var iFrameID = document.getElementById('content-iframe');
      if(iFrameID) {
            // here you can make the height, I delete it first, then I make it again
            iFrameID.height = "";
            iFrameID.height = iFrameID.contentWindow.document.body.scrollHeight + "px";
      }   
  }

function hideNavBars(){
    var navBars = document.getElementsByClassName("nav");
    
    for (var i=0; i<navBars.length; i++){
        navBars[i].style.display = "none";
    }
}

function becomeAdmin(){
    hideNavBars();
    document.getElementById("admin-nav").style.display = "initial";
}

function becomeMentor(){
    hideNavBars();
    document.getElementById("mentor-nav").style.display = "initial";
}

function becomeCodecooler(){
    hideNavBars();
    document.getElementById("codecooler-nav").style.display = "initial";
}
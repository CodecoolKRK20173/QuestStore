var isNicknameBeingChanged = false;
var isPasswordBeingChanged = false;

function changeNickname(){
    if (isNicknameBeingChanged){
        document.getElementById("title").innerHTML = document.getElementById("nickname-input").value;
        document.getElementById("nickname").style.display = "flex";
        document.getElementById("nickname-input").style.display = "none";
        document.getElementById("edit-nickname-button").innerHTML = "Edit nickname";
    } else {
        
        document.getElementById("nickname").style.display = "none";
        document.getElementById("nickname-input").style.display = "initial";
        document.getElementById("nickname-input").value = document.getElementById("title").innerHTML;
        document.getElementById("edit-nickname-button").innerHTML = "Save";
    }
    isNicknameBeingChanged = !isNicknameBeingChanged;
}

function togglePasswordInputs(){
    if (isPasswordBeingChanged){
        document.getElementById("change-password").style.display = "none";
        
        var inputs = document.getElementsByTagName("input");
        for (var i=0; i<inputs.length; i++){
            if (inputs[i].type == "password"){
                inputs[i].value = "";
            }
        }
        
    } else {
        document.getElementById("change-password").style.display = "flex";
        

        location.href = "#change-password";
    }
    
    parent.resizeIframe();
    isPasswordBeingChanged = !isPasswordBeingChanged;
}

function changePassword(){
    togglePasswordInputs();
    alert("Password changed");
}
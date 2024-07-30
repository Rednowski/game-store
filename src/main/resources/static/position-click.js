function positionClick(wrapper) {
    let inner_wrapper = wrapper.getElementsByClassName("position-wrapper")[0];
    if(inner_wrapper.style.display === "none") {
        inner_wrapper.style.display = "block";
    } else {
        inner_wrapper.style.display = "none";
    }

}
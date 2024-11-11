window.addEventListener("load", load_data);

let IS_ANIMATE = true;

document.getElementById('calculator').addEventListener('click', async function (evt) {
    let {x, y} = get_click_coordinates(evt.clientX, evt.clientY);
    try {
        IS_ANIMATE = false;
        await checkPoint([{name: 'x', value: x}, {name: 'y', value: y}]);
    } catch (e){
        alert('Сервер недоступен. Пожалуйста, попробуйте позже');
    }
});

function display_point(x, y, hit){
    if(IS_ANIMATE) completeAnimation();
    IS_ANIMATE = true;
    draw_point(x, y, hit);
}

function completeAnimation(){
    const animatedDiv = document.getElementById('main_form');
    animatedDiv.classList.add('animate');
    animatedDiv.addEventListener('animationend', function() {
        animatedDiv.classList.remove('animate');
    }, { once: true });
}

function load_data() {
    draw_graph(Number(document.getElementById("form:r_slider").value));
    const table = document.querySelector("#archive tbody");
    const rows = table.querySelectorAll("tr");
    rows.forEach(row => {
        let cells = row.querySelectorAll("td");
        if(cells[0].textContent === '') return;
        let x = Number(cells[0].textContent.replace(',', '.'));
        let y = Number(cells[1].textContent.replace(',', '.'));
        let hit = cells[3].textContent === 'Попал';
        draw_point(x, y, hit);
    })
}

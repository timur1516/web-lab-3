window.addEventListener("load", load_data);

document.getElementById('calculator').addEventListener('click', async function (evt) {
    let {x, y} = get_click_coordinates(evt.clientX, evt.clientY);
    try {
        await checkPoint([{name: 'x', value: x}, {name: 'y', value: y}]);
    } catch (e){
        alert('Сервер недоступен. Пожалуйста, попробуйте позже');
    }
});

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

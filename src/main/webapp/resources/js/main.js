const elt = document.getElementById('calculator');
const calculator = Desmos.GraphingCalculator(elt, {
    keypad: false,
    expressions: false,
    settingsMenu: false,
    lockViewport: true,
    zoomFit: true,
    pointsOfInterest: false,
    trace: false,
    xAxisStep: 1,
    yAxisStep: 1,
    showGrid: false
});

function get_click_coordinates(xClick, yClick){
    let calculatorRect = elt.getBoundingClientRect();
    return calculator.pixelsToMath({
        x: xClick - calculatorRect.left,
        y: yClick - calculatorRect.top
    })
}

function draw_point(point, color){
    calculator.setExpression({
        latex: `(${point.x}, ${point.y})`, // Задание координат точки
        color: color // Цвет точки
    });
}

function draw_batman(r) {
    let k = 49 / r
    calculator.setExpression({
        id: 'k',
        latex: 'k=' + k.toString()
    });
    calculator.setExpression({
        id: 's',
        latex: '\\s(x)=\\sqrt{(\\abs(x*k/7))/(x*k/7)}',
        hidden: true
    });
    calculator.setExpression({
        id: 'graph2',
        latex: '(x*k/49)^2*s(\\abs(x*k/7)-3)+(y*k/21)^2*s(y*k/7+3*\\sqrt{33}/7)-1=0',
        color: 'black'
    });
    calculator.setExpression({
        id: 'graph3',
        latex: '\\abs(x*k/14)-((3*\\sqrt{33}-7)/112)*(x*k/7)^2-3+\\sqrt{1-(\\abs(\\abs(x*k/7)-2)-1)^2}-y*k/7=0',
        color: 'black'
    });
    calculator.setExpression({
        id: 'graph4',
        latex: '9*s((1-\\abs(x*k/7))*(\\abs(x*k/7)-.75))-8*\\abs(x*k/7)-y*k/7=0',
        color: 'black'
    });
    calculator.setExpression({
        id: 'graph5',
        latex: '3*\\abs(x*k/7)+.75*s((.75-\\abs(x*k/7))*(\\abs(x*k/7)-.5))-y*k/7=0',
        color: 'black'
    });
    calculator.setExpression({
        id: 'graph6',
        latex: '2.25*s((.5-x*k/7)*(x*k/7+.5))-y*k/7=0',
        color: 'black'
    });
    calculator.setExpression({
        id: 'graph7',
        latex: '6*\\sqrt{10}/7+(1.5-.5*\\abs(x*k/7))*s(\\abs(x*k/7)-1)-6*\\sqrt{10}/14*\\sqrt{4-(\\abs(x*k/7)-1)^2}-y*k/7=0',
        color: 'black'
    });

    calculator.setMathBounds({
        left: -r - 1,
        right: r + 1,
        bottom: -r - 1,
        top: r + 1
    });
}

window.addEventListener("load", load_data);

const message_type = Object.freeze({
    OK: 1,
    EMPTY_FIELDS: 2,
    SOME_SERVER_ERROR: 3,
    CHOOSE_R: 4
});

let selected_r = null;
document.getElementById('calculator').addEventListener('click', async function (evt) {
    // if (selected_r == null) {
    //     show_user_message(message_type.CHOOSE_R);
    //     return;
    // }
    let point = get_click_coordinates(evt.clientX, evt.clientY);
    const hit = await check_point(point.x, point.y, selected_r, false);
    //if(hit == null) return;
    draw_point(point, hit ? 'green' : 'red');
});

document.getElementsByName("r_radio_input").forEach(radiobutton => {
    radiobutton.addEventListener("change", () => {
        selected_r = radiobutton.value;
        draw_batman(Number(selected_r));
    });
});

let active_x_button = null;
document.getElementsByName("x_button_input").forEach(button => {
    button.addEventListener("click", () => {
        if (button === active_x_button) {
            active_x_button.style.borderColor = 'black';
            active_x_button = null;
        } else {
            if (active_x_button !== null) active_x_button.style.borderColor = 'black';
            active_x_button = button;
            active_x_button.style.borderColor = 'red';
        }
    })
});

document.getElementById("form").addEventListener("submit", async () =>{
    await submit_form(event);
});

async function submit_form(event) {
    event.preventDefault();
    //Извлекаем данные формы
    const formData = new FormData(event.target);
    const x = active_x_button == null ? null : active_x_button.value;
    const y = formData.get("y_text_input");
    const r = formData.get("r_radio_input");
    const hit = await check_point(x, y, r, false);
    if(hit == null) return;

    const animatedDiv = document.getElementById('main_form');
    animatedDiv.classList.add('animate');

    // Удаляем класс после завершения анимации, чтобы можно было запустить снова
    animatedDiv.addEventListener('animationend', function() {
        animatedDiv.classList.remove('animate');
        draw_point({x, y}, hit ? 'green' : 'red');
    }, { once: true });
}

async function check_point(x, y, r, redirect) {
    let result = validate_data(x, y, r);
    show_user_message(result);
    if (result !== message_type.OK) return;

    const queryParams = new URLSearchParams();
    queryParams.append("X", x);
    queryParams.append("Y", y);
    queryParams.append("R", r);
    queryParams.append("redirect", redirect);
    try {
        const response = await fetch(`controller?${queryParams.toString()}`);
        if (response.redirected ^ redirect) {
            show_user_message(message_type.SOME_SERVER_ERROR);
            return;
        }
        if (redirect){
            window.location.href = response.url;
            return;
        }

        const data = await response.json();
        add_data_to_history(
            data.x,
            data.y,
            data.r,
            data.hit,
            data.calculationTime,
            new Date(data.time[0], data.time[1] - 1, data.time[2], data.time[3], data.time[4], data.time[5])
        );
        return data.hit;
    } catch (e) {
        show_user_message(message_type.SOME_SERVER_ERROR);
    }
}

function add_data_to_history(x, y, r, hit, execution_time, real_time) {
    let table_ref = document.querySelector("#history_table tbody");
    let newRow = table_ref.insertRow(0);
    [
        x.toFixed(2).toString(),
        y.toFixed(2).toString(),
        r.toFixed(2).toString(),
        hit ? "Попал" : "Промазал",
        `${real_time.toLocaleDateString()} ${real_time.toLocaleTimeString()}`,
        execution_time.toString() + "мкс"
    ].forEach(value => newRow.insertCell().textContent = value);
}

function show_user_message(message) {
    let error_field = document.getElementById("error_field");
    error_field.style.visibility = "visible";
    switch (message) {
        case message_type.EMPTY_FIELDS:
            error_field.textContent = "Пожалуйста, заполните все поля!";
            break;
        case message_type.SOME_SERVER_ERROR:
            error_field.textContent = "Упс... Произошла ошибка при работе с сервером. Пожалуйста, повторите попытку позже.";
            break;
        case message_type.CHOOSE_R:
            error_field.textContent = "Невозможно определить координаты точки! Пожалуйста, выберете R!";
            break;
        default:
            error_field.style.visibility = "hidden";
            error_field.textContent = "";
            break;
    }
}

function load_data() {
    draw_batman(Number(document.getElementById("form:r_slider").value));
    const table = document.querySelector("#archive tbody");
    const rows = table.querySelectorAll("tr");
    rows.forEach(row =>{
        let cells = row.querySelectorAll("td");
        let x = Number(cells[0].textContent);
        let y = Number(cells[1].textContent);
        let hit = cells[3].textContent === 'Попал';
        draw_point({x,y}, hit ? 'green' : 'red');
    })
}
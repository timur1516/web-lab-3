const colors = ['#FF0000', '#00FF00', '#0000FF', '#FF0000']; // Переход от красного к зеленому, синему и обратно
const steps = 100; // Количество шагов для одного цикла перехода

let step = 0; // Начальный шаг

// Функция для интерполяции цвета между двумя цветами
function interpolateColor(color1, color2, factor) {
    const c1 = parseInt(color1.slice(1), 16);
    const c2 = parseInt(color2.slice(1), 16);

    const r = Math.round(((c2 >> 16) - (c1 >> 16)) * factor + (c1 >> 16));
    const g = Math.round((((c2 >> 8) & 0xff) - ((c1 >> 8) & 0xff)) * factor + ((c1 >> 8) & 0xff));
    const b = Math.round(((c2 & 0xff) - (c1 & 0xff)) * factor + (c1 & 0xff));

    return `#${(r << 16 | g << 8 | b).toString(16).padStart(6, '0')}`;
}

// Функция для плавного перехода между цветами
function getColor(step) {
    const colorIndex = Math.floor((step / steps) * (colors.length - 1));
    const color1 = colors[colorIndex];
    const color2 = colors[(colorIndex + 1) % colors.length];
    const factor = (step % (steps / (colors.length - 1))) / (steps / (colors.length - 1));

    return interpolateColor(color1, color2, factor);
}

// Функция для изменения box-shadow с использованием циклического градиента
function changeBoxShadowColor() {
    const color = getColor(step);
    document.querySelectorAll('#clocks, #header, #footer').forEach(element => {
        element.style.boxShadow = `0 0 10px 10px ${color}`;
    });

    // Увеличиваем шаг; если достигли конца, начинаем с начала
    step = (step + 1) % steps;
}

// Запускаем функцию каждые 100 мс
setInterval(changeBoxShadowColor, 100);
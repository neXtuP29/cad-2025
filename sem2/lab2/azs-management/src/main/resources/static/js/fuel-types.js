// JavaScript для страницы управления типами топлива

document.addEventListener('DOMContentLoaded', function() {
    initializeFuelTypesPage();
});

function initializeFuelTypesPage() {
    addPriceUpdateHandlers();
    addRealTimeValidation();
    initializeFuelTypeSorting();
    addSearchFunctionality();
}

// Обработчики обновления цен
function addPriceUpdateHandlers() {
    const priceForms = document.querySelectorAll('form[action*="/fuel-types/update"]');
    priceForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();

            const priceInput = this.querySelector('input[name="price"]');
            const newPrice = parseFloat(priceInput.value);

            if (isValidPrice(newPrice)) {
                updatePrice(this, newPrice);
            } else {
                highlightInvalidPrice(priceInput);
                AZS.showToast('Введите корректную цену (положительное число)', 'error');
            }
        });
    });
}

// Валидация цены в реальном времени
function addRealTimeValidation() {
    const priceInputs = document.querySelectorAll('input[name="price"]');
    priceInputs.forEach(input => {
        input.addEventListener('input', function() {
            const price = parseFloat(this.value);
            if (isValidPrice(price)) {
                this.style.borderColor = '#28a745';
                this.style.boxShadow = '0 0 0 0.2rem rgba(40, 167, 69, 0.25)';
            } else {
                this.style.borderColor = '#dc3545';
                this.style.boxShadow = '0 0 0 0.2rem rgba(220, 53, 69, 0.25)';
            }
        });
    });
}

// Проверка валидности цены
function isValidPrice(price) {
    return !isNaN(price) && price > 0 && price < 1000;
}

// Подсветка невалидной цены
function highlightInvalidPrice(input) {
    input.style.borderColor = '#dc3545';
    input.style.boxShadow = '0 0 0 0.2rem rgba(220, 53, 69, 0.25)';

    setTimeout(() => {
        input.style.borderColor = '';
        input.style.boxShadow = '';
    }, 3000);
}

// Обновление цены (можно заменить на AJAX)
function updatePrice(form, newPrice) {
    // Показываем индикатор загрузки
    const submitButton = form.querySelector('button[type="submit"]');
    const originalText = submitButton.innerHTML;
    submitButton.innerHTML = '⏳';
    submitButton.disabled = true;

    // Имитация задержки сети
    setTimeout(() => {
        form.submit();
    }, 500);
}

// Сортировка типов топлива
function initializeFuelTypeSorting() {
    const table = document.querySelector('.table');
    if (table) {
        const headers = table.querySelectorAll('th');
        headers.forEach((header, index) => {
            if (index !== headers.length - 1) { // Исключаем колонку действий
                header.style.cursor = 'pointer';
                header.addEventListener('click', () => {
                    sortTable(index);
                });
            }
        });
    }
}

function sortTable(columnIndex) {
    const table = document.querySelector('.table tbody');
    const rows = Array.from(table.querySelectorAll('tr'));

    rows.sort((a, b) => {
        const aValue = a.cells[columnIndex].textContent.trim();
        const bValue = b.cells[columnIndex].textContent.trim();

        if (columnIndex === 2) { // Колонка цены
            return parseFloat(aValue) - parseFloat(bValue);
        }

        return aValue.localeCompare(bValue);
    });

    // Очищаем таблицу и добавляем отсортированные строки
    table.innerHTML = '';
    rows.forEach(row => table.appendChild(row));

    AZS.showToast('Таблица отсортирована', 'success');
}

// Поиск по типам топлива
function addSearchFunctionality() {
    const searchContainer = document.createElement('div');
    searchContainer.className = 'mb-3';
    searchContainer.innerHTML = `
        <div class="input-group">
            <input type="text" id="fuelSearch" class="form-control" placeholder="Поиск по названию или описанию...">
            <button class="btn btn-outline-secondary" type="button" id="clearSearch">Очистить</button>
        </div>
    `;

    const table = document.querySelector('.table');
    if (table) {
        table.parentNode.insertBefore(searchContainer, table);

        const searchInput = document.getElementById('fuelSearch');
        const clearButton = document.getElementById('clearSearch');

        searchInput.addEventListener('input', performSearch);
        clearButton.addEventListener('click', clearSearch);
    }
}

function performSearch() {
    const searchTerm = this.value.toLowerCase();
    const rows = document.querySelectorAll('.table tbody tr');

    rows.forEach(row => {
        const name = row.cells[0].textContent.toLowerCase();
        const description = row.cells[1].textContent.toLowerCase();

        if (name.includes(searchTerm) || description.includes(searchTerm)) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}

function clearSearch() {
    const searchInput = document.getElementById('fuelSearch');
    searchInput.value = '';

    const rows = document.querySelectorAll('.table tbody tr');
    rows.forEach(row => {
        row.style.display = '';
    });
}

// Расчет общей стоимости
function calculateTotalCost() {
    const priceInputs = document.querySelectorAll('input[name="price"]');
    let total = 0;

    priceInputs.forEach(input => {
        total += parseFloat(input.value) || 0;
    });

    return total.toFixed(2);
}

// Экспорт функций для страницы топлива
window.FuelTypes = {
    isValidPrice,
    calculateTotalCost,
    sortTable
};
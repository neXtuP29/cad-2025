// Основные JavaScript функции для приложения АЗС

document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

function initializeApp() {
    addFormValidation();
    addConfirmationHandlers();
    initializeAnimations();
    loadStatistics();
}

// Валидация форм
function addFormValidation() {
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const requiredFields = form.querySelectorAll('[required]');
            let isValid = true;

            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    isValid = false;
                    highlightInvalidField(field);
                } else {
                    removeInvalidHighlight(field);
                }
            });

            if (!isValid) {
                e.preventDefault();
                showToast('Пожалуйста, заполните все обязательные поля', 'error');
            }
        });
    });
}

// Подсветка невалидных полей
function highlightInvalidField(field) {
    field.style.borderColor = '#dc3545';
    field.style.boxShadow = '0 0 0 0.2rem rgba(220, 53, 69, 0.25)';
}

function removeInvalidHighlight(field) {
    field.style.borderColor = '';
    field.style.boxShadow = '';
}

// Обработчики подтверждения действий
function addConfirmationHandlers() {
    const deleteButtons = document.querySelectorAll('a[onclick*="confirm"]');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            const message = this.getAttribute('onclick').match(/'([^']+)'/)[1];
            if (!confirm(message)) {
                e.preventDefault();
            }
        });
    });
}

// Анимации
function initializeAnimations() {
    // Анимация появления элементов
    const animatedElements = document.querySelectorAll('.station-card, .table tbody tr');
    animatedElements.forEach((element, index) => {
        element.style.opacity = '0';
        element.style.transform = 'translateY(20px)';

        setTimeout(() => {
            element.style.transition = 'all 0.5s ease';
            element.style.opacity = '1';
            element.style.transform = 'translateY(0)';
        }, index * 100);
    });
}

// Загрузка статистики
function loadStatistics() {
    // Здесь можно добавить загрузку дополнительной статистики через AJAX
    console.log('Статистика загружена');
}

// Функция для показа уведомлений
function showToast(message, type = 'info') {
    const toast = document.createElement('div');
    toast.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
    toast.style.cssText = `
        top: 20px;
        right: 20px;
        z-index: 1050;
        min-width: 300px;
    `;
    toast.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;

    document.body.appendChild(toast);

    // Автоматическое скрытие через 5 секунд
    setTimeout(() => {
        if (toast.parentNode) {
            toast.parentNode.removeChild(toast);
        }
    }, 5000);
}

// Утилиты для работы с формами
function clearForm(formId) {
    const form = document.getElementById(formId);
    if (form) {
        form.reset();
        showToast('Форма очищена', 'info');
    }
}

// Обработка изменения статуса АЗС
function handleStationStatusChange(stationId, newStatus) {
    // Здесь можно добавить AJAX запрос для обновления статуса
    console.log(`Статус АЗС ${stationId} изменен на: ${newStatus}`);
}

// Экспорт функций для глобального использования
window.AZS = {
    showToast,
    clearForm,
    handleStationStatusChange
};
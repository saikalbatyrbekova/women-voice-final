(function ($) {
    "use strict";

    // Dropdown on mouse hover
    $(document).ready(function () {
        function toggleNavbarMethod() {
            if ($(window).width() > 992) {
                $('.navbar .dropdown').on('mouseover', function () {
                    $('.dropdown-toggle', this).trigger('click');
                }).on('mouseout', function () {
                    $('.dropdown-toggle', this).trigger('click').blur();
                });
            } else {
                $('.navbar .dropdown').off('mouseover').off('mouseout');
            }
        }

        toggleNavbarMethod();
        $(window).resize(toggleNavbarMethod);
    });


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        dots: true,
        loop: true,
        items: 1
    });

})(jQuery);

function toggleReplies(button) {
    // Находим контейнер с ответами
    var repliesDiv = button.closest('.media-body').querySelector('.replies');

    if (repliesDiv) {
        // Переключаем видимость блока с ответами
        if (repliesDiv.style.display === "none" || repliesDiv.style.display === "") {
            repliesDiv.style.display = "block";
            button.textContent = "Hide Replies"; // Меняем текст кнопки
        } else {
            repliesDiv.style.display = "none";
            button.textContent = "Show Replies"; // Меняем текст кнопки
        }
    }
}

// Показываем поле для ввода комментария
function toggleReplyInput(button) {
    var replyInputDiv = button.nextElementSibling; // Это div с textarea
    var sendButton = replyInputDiv.querySelector('button'); // Кнопка "Send"

    // Переключаем видимость поля для ввода
    if (replyInputDiv.style.display === "none" || replyInputDiv.style.display === "") {
        replyInputDiv.style.display = "block";
        sendButton.style.display = "inline-block";
    } else {
        replyInputDiv.style.display = "none";
        sendButton.style.display = "none";
    }
}

// Функция для получения токена из cookies
function getCookie(name) {
    let match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    if (match) return match[2];
    return null;
}

// Функция для отправки ответа на комментарий
function sendReply(button) {
    var replyInput = button.previousElementSibling; // Это textarea
    var replyText = replyInput.value;
    var parentId = button.getAttribute('data-parent-id'); // Получаем parentId из data-атрибута кнопки

    if (replyText.trim() === "") {
        alert("Please write something before sending.");
        return;
    }

    // Получаем токен из cookies
    var token = getCookie("access_token");

    if (!token) {
        alert("You need to be logged in to reply.");
        return;
    }

    // Создаем объект с данными для отправки на сервер
    var requestData = {
        content: replyText
    };

    // Отправляем POST запрос с использованием fetch
    fetch(`/comments/reply/${parentId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json())
        .then(data => {
            console.log("Reply added:", data);
        })
        .catch(error => {
            console.error("Error adding reply:", error);
        });

    // Очистка поля после отправки
    replyInput.value = "";
    button.style.display = "none"; // Скрыть кнопку "Send"
}


// Функция для отправки комментария
function submitComment(event) {
    event.preventDefault();  // Останавливает стандартное поведение формы

    const commentText = document.getElementById('message').value; // Получаем текст комментария
    const postId = event.target.querySelector('input[type="submit"]').getAttribute('data-post-id');  // Извлекаем postId из кнопки отправки

    // Получаем токен авторизации из cookie
    const token = getCookie('access_token');

    // Если комментарий пустой, не отправляем запрос
    if (!commentText.trim()) {
        alert('Пожалуйста, введите текст комментария.');
        return;
    }

    // Подготовка данных для отправки
    const requestData = {
        content: commentText
    };

    // Отправка комментария на сервер
    fetch(`/comments/${postId}`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            alert('Комментарий успешно отправлен!');
            document.getElementById('message').value = '';  // Очищаем поле ввода
        })
        .catch(error => {
            console.error('Ошибка при отправке комментария:', error);
            alert('Ошибка при отправке комментария. Попробуйте позже.');
        });
}


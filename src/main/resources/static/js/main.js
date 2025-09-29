// 기본 JavaScript 기능들

// 확인 대화상자
function confirmDelete(message) {
    return confirm(message || '정말 삭제하시겠습니까?');
}

// 폼 제출 전 확인
function confirmSubmit(message) {
    return confirm(message || '제출하시겠습니까?');
}

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', function() {

    // 파일 크기 체크
    const fileInputs = document.querySelectorAll('input[type="file"]');
    fileInputs.forEach(function(input) {
        input.addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const maxSize = 10 * 1024 * 1024; // 10MB
                if (file.size > maxSize) {
                    alert('파일 크기는 10MB를 초과할 수 없습니다.');
                    e.target.value = '';
                    return false;
                }
            }
        });
    });

    // 자동 숨김 알림
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        if (alert.classList.contains('alert-success')) {
            setTimeout(function() {
                alert.style.opacity = '0';
                setTimeout(function() {
                    alert.style.display = 'none';
                }, 300);
            }, 3000);
        }
    });

    // 텍스트 영역 자동 크기 조정
    const textareas = document.querySelectorAll('textarea');
    textareas.forEach(function(textarea) {
        textarea.addEventListener('input', function() {
            this.style.height = 'auto';
            this.style.height = this.scrollHeight + 'px';
        });
    });

});

// 폼 유효성 검사
function validateForm(formId) {
    const form = document.getElementById(formId);
    if (!form) return true;

    const requiredFields = form.querySelectorAll('[required]');
    let isValid = true;

    requiredFields.forEach(function(field) {
        if (!field.value.trim()) {
            field.classList.add('is-invalid');
            isValid = false;
        } else {
            field.classList.remove('is-invalid');
        }
    });

    return isValid;
}

// 동적 시간 업데이트
function updateTimeElements() {
    const timeElements = document.querySelectorAll('[data-time]');
    timeElements.forEach(function(element) {
        const timestamp = element.getAttribute('data-time');
        const date = new Date(timestamp);
        const now = new Date();
        const diff = now - date;

        if (diff < 60000) { // 1분 미만
            element.textContent = '방금 전';
        } else if (diff < 3600000) { // 1시간 미만
            element.textContent = Math.floor(diff / 60000) + '분 전';
        } else if (diff < 86400000) { // 24시간 미만
            element.textContent = Math.floor(diff / 3600000) + '시간 전';
        }
    });
}

// 페이지 로드 시 시간 업데이트
document.addEventListener('DOMContentLoaded', updateTimeElements);

// 1분마다 시간 업데이트
setInterval(updateTimeElements, 60000);

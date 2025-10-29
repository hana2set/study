## jQuery
- 기본
    ```js
    let isValidFormat = {
        date: function(d) {
            if (typeof d !== 'string') return false;

            const match = d.match(/^(\d{4})-(\d{2})-(\d{2})$/);
            if (!match) return false;

            const year = parseInt(match[1], 10);
            const month = parseInt(match[2], 10);
            const day = parseInt(match[3], 10);

            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;

            const dt = new Date(year, month - 1, day);
            return dt.getFullYear() === year && (dt.getMonth() + 1) === month && dt.getDate() === day;
        },
        regNo: function(d) {
            return typeof d === 'string' && /(\d{6})-(\d{7})/.test(d);
        },
        bizNo: function(d) {
            return typeof d === 'string' && /(\d{3})-(\d{2})-(\d{5})/.test(d);
        },
    }

    const FORMAT_ERROR_MESSAGES = {
        date: '날짜 형식(yyyy-mm-dd)이 유효하지 않습니다.',
        regNo: '주민등록번호는 13자리 수(000000-0000000)를 입력해야 입니다.',
        bizNo: '사업자등록번호는 10자리 수(000-00-00000)를 입력해야 합니다.'
    }
    ```
- 확인
    ```js
    for (let obj of $("[data-format]")) {	
        const type = $(obj).data("format");
        const validator = isValidFormat[type];
        const value = obj.value;
        
        // 유효성 검사 함수가 존재하고, 값이 있으며, 검사에 실패한 경우
        if (typeof validator === 'function' && value && !validator(value)) {
            alert(FORMAT_ERROR_MESSAGES[type]);
            obj.focus();
            return false;
        }
    }
    ```
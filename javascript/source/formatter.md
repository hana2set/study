

## jQuery
기본적으로는 formatter 라이브러리 사용하는 것 추천
- 기본
    ```js
		var formatter = {
			onlyNumber: (str) => str.replace(/\D/g, ''),
			price: (str) => str.replace(/\D/g, '').replace(/\B(?=(\d{3})+(?!\d))/g, ","),
			date: (str) => str.replace(/\D/g, '').replace(/(\d{4})(\d{2})(\d{2}).*/, "$1-$2-$3"),
			regNo: (str) => String(str).replace(/\D/g, '').replace(/(\d{6})(\d{1,7}).*/, "$1-$2"),
			bizNo: (str) => String(str).replace(/\D/g, '').replace(/(\d{3})(\d{2})(\d{1,5}).*/, "$1-$2-$3"),
		}

		var formatManager = {
			init: function() {
				// 데이터 초기화
				$("[data-format]").each(function() {
					const type = $(this).data("format");
					if (type == 'date') $(this).datepicker();
					if (type && typeof formatter[type] === 'function') $(this).val(formatter[type]($(this).val()));
				});

				// 이벤트 초기화
				$(document)
					.on("focus", '[data-format]:not([readonly])', function() {
						$(this).val(formatter.onlyNumber($(this).val()));
					})
					.on("blur", '[data-format]:not([readonly])', function() {
						const type = $(this).data("format");
						if (type && typeof formatter[type] === 'function') $(this).val(formatter[type]($(this).val()));
					});
			},
			unformatAll: function () {
				$("[data-format]").each(function() {
					$(this).val(formatter.onlyNumber($(this).val()));
				});
			},
			formatAll: function () {
				$("[data-format]").each(function() {
					const type = $(this).data("format");
					if (type == 'date') $(this).datepicker();
					if (type && typeof formatter[type] === 'function') $(this).val(formatter[type]($(this).val()));
				});
			}
		}
    ```
    - 참고사항:
      - 금액관련
        - jQuery는 beforeinput 없음 (최신 event)
        - input이 더 나은 방식이나, 코드가 복잡해짐 (요구사항 확인할 것)
        - keydown은 복붙이나 드래그 못 막음 (막으면 복붙, 전체선택 등 다 확인해줘야함)
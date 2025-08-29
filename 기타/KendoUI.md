# KendoUI

UI 프레임워크로, 웹, 모바일, PC 등의 크로스 플랫폼을 지원함.
jQuery, Angular, React, Vue 프레임워크를 제공.

## [Kendo UI for jQuery](https://www.telerik.com/kendo-jquery-ui/documentation/introduction)

- video 기초 강의: https://www.youtube.com/playlist?list=PLLGlTD7u3kMqNNdCJT3WJ113vTNiCCQpN
  - chap1: grid, chart, scheduler -> pro (유료)
  - chap2: 예제 다운로드 (mvp)
  - chap3: cdn, npm 등 활용 / 모듈화 되어있어 필요 모듈만 추가해 번들 크기 최적화 할 수 있음 /
  - chap4: 기본 컴포넌트 변환 예제. (Button, Dropdownlist, slider, tabstrip, combobox)
    - 기본 템플릿보다 제공하는 templates를 사용하면 성능 이점(`# = #`)
  - chap5: chart (data binding, category, axis, series, type)
  - chap6: grid (columns, filter, scollerable, paging, selectable)
    - 커스텀 - row template 참고
  - chap7: 제공(+custom) style, theme, icons

### GRID

- schema.model은 [kendo.data.model.define](https://www.telerik.com/kendo-jquery-ui/documentation/api/javascript/data/model/methods/define)으로 정의됨
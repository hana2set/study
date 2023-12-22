# HATEOAS 란?
- **Hypermedia As The Engine of Application State 의 약자**
- REST Api를 사용하는 클라이언트가 전적으로 서버와 동적인 상호작용이 가능하도록 하는 것을 의미
- 클라이언트가 서버로부터 어떠한 요청을 할 때, 요청에 필요한 URI를 응답에 포함시켜 반환하는 것
    - 이를 통해 클라이언트가 서버 API 변경으로부터 분리됨 -> 종속성 ↓



## Spring boot HATEOAS 적용 방법 (페이징 예시)
1. HATEOAS 의존성 추가 (spring-boot-starter-hateoas)
    ```java
    // 9. SpringBoot HATEOAS 의존성 추가
    implementation 'org.springframework.boot:spring-boot-starter-hateoas'
    ```

2. 핸들러 매개변수로 PagedResourcesAssembler 넣고 PagedModel 로 응답
    ```java
    @RestController
    public class ChannelController {

        @Autowired
        ChannelRepository channelRepository;

        @GetMapping("/channels")
        public PagedModel<User> getUsers(Pageable pageable,  PagedResourcesAssembler<User> assembler) {
            var all = channelRepository.findAll(pageable);
            return assembler.toModel(all);
        }

    }
    ​```

- 적용 전 응답 (기본 Pageable)
    ```json
    {  
        "content":[  
        ...
            {  
                "id":140,
                "title":"jpa"
            }
        ],
        "pageable":{  
            "sort":{  
                "sorted":true,
                "unsorted":false
            },
            "offset":20,
            "pageSize":10,
            "pageNumber":2,
            "unpaged":false,
            "paged":true
        },
        "totalElements":200,
        "totalPages":20,
        "last":false,
        "size":10,
        "number":2,
        "first":false,
        "numberOfElements":10,
        "sort":{  
            "sorted":true,
            "unsorted":false
        }
    }
    ​```
- 적용 후 응답 (PageModel (PagedResource))
    ```json
    {  
        "_embedded":{  
            "channelList":[  
                {  
                    "id":140,
                    "title":"jpa"
                },
                ...
                {  
                    "id":109,
                    "title":"jpa"
                }
            ]
        },
        "_links":{  
            "first":{  
                "href":"http://localhost/channels?page=0&size=10&sort=created,desc&sort=title,asc"
            },
            "prev":{  
                "href":"http://localhost/channels?page=1&size=10&sort=created,desc&sort=title,asc"
            },
            "self":{  
                "href":"http://localhost/channels?page=2&size=10&sort=created,desc&sort=title,asc"
            },
            "next":{  
                "href":"http://localhost/channels?page=3&size=10&sort=created,desc&sort=title,asc"
            },
            "last":{  
                "href":"http://localhost/channels?page=19&size=10&sort=created,desc&sort=title,asc"
            }
        },
        "page":{  
            "size":10,
            "totalElements":200,
            "totalPages":20,
            "number":2
        }
    }
    ```
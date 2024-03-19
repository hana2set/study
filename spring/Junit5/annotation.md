___
* @SpringBootTest
* @WebMvcTest
* @DataJpaTest

* @ExtendWith(SpringExtension.class)
* @ExtendWith(MockitoExtension.class)
    * https://www.baeldung.com/junit-5-extensions
___
* @BeforeEach
* @BeforeAll
* @AfterEach
* @AfterAll
* @Mock 
* @InjectMocks - mock 객체들 중 필요 객체 자동 주입
* @Disabled
* @DisplayName("display name")  
___
* @Nested - 내부 클래스 생성으로 함수 그룹화 가능  
* @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
* @Order(1)
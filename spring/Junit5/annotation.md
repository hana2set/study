___
* @SpringBootTest
* @WebMvcTest
* @DataJpaTest  
  <br>
* @ExtendWith(SpringExtension.class)
* @ExtendWith(MockitoExtension.class)
    * https://www.baeldung.com/junit-5-extensions
___
* @BeforeEach
* @BeforeAll
* @AfterEach
* @AfterAll
* @Mock - Mockito.mock() 메서드. mock 객체 주입 (MockitoExtension.class 필요)
* @MockitoBean - **Spring Context 내의 bean**에서 주입해주는 @Mock (SpringExtension.class 필요)
* @InjectMocks - mock 객체들 중 필요 객체 자동 주입
* @Disabled
* @DisplayName("display name")  
___
* @Nested - 내부 클래스 생성으로 함수 그룹화 가능  
* @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
* @Order(1)


---

junit4 | junit5
@Before | @BeforeAll
@After | @AfterEach
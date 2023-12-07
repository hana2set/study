### RestTemplate은 http 통신 시 내부에서 어떤 컨버터를 사용하는지? 


MappingJackson2HttpMessageConverter  
-> AbstractGenericHttpMessageConverter  
-> readJavaType method  
-> ObjectMapper 사용

```java
/**
 * Create a new instance of the {@link RestTemplate} using default settings.
 * Default {@link HttpMessageConverter HttpMessageConverters} are initialized.
 */
public RestTemplate() {
		this.messageConverters.add(new ByteArrayHttpMessageConverter());
		this.messageConverters.add(new StringHttpMessageConverter());
		this.messageConverters.add(new ResourceHttpMessageConverter(false));

		this.messageConverters.add(new AllEncompassingFormHttpMessageConverter());

		if (romePresent) {
			this.messageConverters.add(new AtomFeedHttpMessageConverter());
			this.messageConverters.add(new RssChannelHttpMessageConverter());
		}

		if (jackson2XmlPresent) {
			this.messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
		}
		else if (jaxb2Present) {
			this.messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
		}

		if (kotlinSerializationProtobufPresent) {
			this.messageConverters.add(new KotlinSerializationProtobufHttpMessageConverter());
		}

		if (kotlinSerializationJsonPresent) {
			this.messageConverters.add(new KotlinSerializationJsonHttpMessageConverter());
		}
		if (jackson2Present) {
			this.messageConverters.add(new MappingJackson2HttpMessageConverter());
		}
		else if (gsonPresent) {
			this.messageConverters.add(new GsonHttpMessageConverter());
		}
		else if (jsonbPresent) {
			this.messageConverters.add(new JsonbHttpMessageConverter());
		}

		if (jackson2SmilePresent) {
			this.messageConverters.add(new MappingJackson2SmileHttpMessageConverter());
		}

		if (jackson2CborPresent) {
			this.messageConverters.add(new MappingJackson2CborHttpMessageConverter());
		}
		else if (kotlinSerializationCborPresent) {
			this.messageConverters.add(new KotlinSerializationCborHttpMessageConverter());
		}

		updateErrorHandlerConverters();
		this.uriTemplateHandler = initUriTemplateHandler();
	}
```


```java

	private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) throws IOException {
		MediaType contentType = inputMessage.getHeaders().getContentType();
		Charset charset = getCharset(contentType);

		ObjectMapper objectMapper = selectObjectMapper(javaType.getRawClass(), contentType);
		Assert.state(objectMapper != null, () -> "No ObjectMapper for " + javaType);

		boolean isUnicode = ENCODINGS.containsKey(charset.name()) ||
				"UTF-16".equals(charset.name()) ||
				"UTF-32".equals(charset.name());
		try {
			InputStream inputStream = StreamUtils.nonClosing(inputMessage.getBody());
			if (inputMessage instanceof MappingJacksonInputMessage mappingJacksonInputMessage) {
				Class<?> deserializationView = mappingJacksonInputMessage.getDeserializationView();
				if (deserializationView != null) {
					ObjectReader objectReader = objectMapper.readerWithView(deserializationView).forType(javaType);
					objectReader = customizeReader(objectReader, javaType);
					if (isUnicode) {
						return objectReader.readValue(inputStream);
					}
					else {
						Reader reader = new InputStreamReader(inputStream, charset);
						return objectReader.readValue(reader);
					}
				}
			}

			ObjectReader objectReader = objectMapper.reader().forType(javaType);
			objectReader = customizeReader(objectReader, javaType);
			if (isUnicode) {
				return objectReader.readValue(inputStream);
			}
			else {
				Reader reader = new InputStreamReader(inputStream, charset);
				return objectReader.readValue(reader);
			}
		}
		catch (InvalidDefinitionException ex) {
			throw new HttpMessageConversionException("Type definition error: " + ex.getType(), ex);
		}
		catch (JsonProcessingException ex) {
			throw new HttpMessageNotReadableException("JSON parse error: " + ex.getOriginalMessage(), ex, inputMessage);
		}
	}
```
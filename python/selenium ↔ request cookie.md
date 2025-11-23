- selenium to request
  ```py
  cookies = driver.get_cookies()
  requests_cookies = {}
  for c in cookies:
      requests_cookies[c['name']] = c['value']    

  response = requests.get('http://some-host...', cookies=requests_cookies)    
  ```

- request to selenium
  ```py
  cookies = session.cookies.get_dict()
  for name, value in cookies.items():
      driver.delete_cookie(name)
      driver.add_cookie({
          "name": name,
          "value": value,
          "domain": config.URL.split("://")[1]
      })
  ```



  ---

  http://selenium.dev/documentation/webdriver/interactions/cookies/
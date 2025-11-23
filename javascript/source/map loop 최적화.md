```javascript
for (const [key, value] of Object.entries(json)) {
  if (key === "count" && value > 2) {
    console.log("카운트 초과");
    break;
  }
}
```
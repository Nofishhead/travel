# 用户接口文档

## 用户登录
- 请求路径：/travel/yonghu/login
- 请求方法：POST
- 请求参数： 
- json
{
    "username": "string",
    "password": "string"
}
- 响应结果：
```json
{
    "code": "200",
    "msg": "success",
    "data": {
        "token": "string",
        "userInfo": {
            "id": "number",
            "username": "string",
            "email": "string",
            "phone": "string"
        }
    }
}
```

## 用户注册
- 请求路径：/travel/yonghu/register
- 请求方法：POST
- 请求参数：
```json
{
    "username": "string",
    "password": "string",
    "email": "string",
    "phone": "string"
}
```
- 响应结果：
```json
{
    "code": "200",
    "msg": "注册成功",
    "data": null
}
```

## 用户退出登录
- 请求路径：/travel/yonghu/logout
- 请求方法：POST
- 请求头：
```json
{
    "token": "string"
}
```
- 响应结果：
```json
{
    "code": "200",
    "msg": "退出成功",
    "data": null
}
```
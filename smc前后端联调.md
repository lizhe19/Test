# smc前后端联调

## 一、 日志监视接口

**获取应用名称和id接口**

*   接口类型
    

|  获取本机ini文件中的应用名称和id  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/log/app/info  |
|  请求方式  |  GET  |

*   请求参数
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|   |   |   |   |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  \[\]AppNameAndId  |  具体的返回数据  |

AppNameAndId

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  AppId  |  string  |  应用ID  |
|  AppName  |  string  |  应用名称  |

**获取应用Id对应的日志文件名称列表**

*   接口类型
    

|  获取应用Id对应的日志文件名称列表  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/log/fileName  |
|  请求方式  |  GET  |

*   请求参数
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  appId  |  string  |  是  |  应用id  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  \[\]string  |  具体的返回数据:日志文件名称  |

**获取日志树-本机节点上的应用及对应的日志文件**

*   接口类型
    

|  获取日志树  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/log/all/fileName  |
|  请求方式  |  GET  |

*   请求参数
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|   |   |   |   |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  \[\]AppLogInfo  |  具体的返回数据  |

AppLogInfo

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  appId  |  string  |  应用ID  |
|  appName  |  string  |  应用名称  |
|  fileNames  |  \[\]string  |  日志文件名称列表  |

**获取单个运行日志文件流**

*   接口类型
    

|  获取日志树  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/log/file/info  |
|  请求方式  |  GET  |

*   请求参数-url上拼接
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  appId  |  string  |  是  |  应用id  |
|  fileName  |  string  |  是  |  日志文件名称  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |  错误信息  |
|  data  |  body  |   |

获取日志文件修改时间是否变动

*   接口类型
    

|  获取日志树  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/log/file/ischange  |
|  请求方式  |  POST  |

*   请求参数
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  appId  |  string  |  是  |  应用id  |
|  fileName  |  string  |  是  |  日志文件名称  |
|  oldModTime  |  long  |  是  |  文件修改时间  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |  错误信息  |
|  data  |  bool  |  是否有改变  |

**自定义导出运行日志文件-批量**

*   接口类型
    

|  获取日志树  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/log/custom/download  |
|  请求方式  |  POST  |

*   请求参数
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  appLogInfos  |  \[\]AppLogInfo  |  是  |  导出的应用id和日志文件名称  |

AppLogInfo

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  appId  |  string  |  是  |  导出的应用id  |
|  appName  |  string  |  否  |  导出的应用名称  |
|  fileNames  |  \[\]string  |  否  |  日志文件名称  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |  错误信息  |
|  data  |  string  |  导出压缩包名称  |

**自定义导出文件服务器**

*   接口类型
    

|  获取日志树  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/log/batch/download/导出压缩包名称  |
|  请求方式  |  Static  |

**查询操作日志列表-包含过滤条件**

*   接口类型
    

|  获取日志树  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/log/oplog  |
|  请求方式  |  POST  |

*   请求参数
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  startTime  |  int  |  否  |  开始时间戳  |
|  endTime  |  int  |  否  |  结束时间戳  |
|  operator  |  string  |  否  |  操作人  |
|  location  |  string  |  否  |  操作位置  |
|  desc  |  string  |  否  |  操作描述  |
|  pageNum  |  int  |  否  |  页数  |
|  pageSize  |  int  |  否  |  每页个数  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  OpLogResp  |  具体的返回数据  |

OpLogResp

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  pageNum  |  int  |  页数  |
|  pageSize  |  int  |  每页个数  |
|  total  |  int  |  总数  |
|  oplog  |  \[\]Oplog  |  具体的日志信息列表  |

Oplog

|  参数名  |  数据类型  |  说明  |
| --- | --- | --- |
|  id  |  int  |  id主键  |
|  opTime  |  time  |  操作时间  |
|  operator  |  string  |  操作人  |
|  opObj  |  stirng  |  操作对象  |
|  location  |  string  |  操作位置  |
|  desc  |  string  |  操作描述  |
|  fromSk  |  bool  |  是否来自sk  |

**导出操作日志-流文件 csv格式**

*   接口类型
    

|  获取日志树  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/log/download/oplog  |
|  请求方式  |  POST  |

*   请求参数
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  startTime  |  int  |  否  |  开始时间戳  |
|  endTime  |  int  |  否  |  结束时间戳  |
|  operator  |  string  |  否  |  操作人  |
|  location  |  string  |  否  |  操作位置  |
|  desc  |  string  |  否  |  操作描述  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  object  |   |

## 二、系统监视相关接口

**获取系统监视参数**

*   接口类型
    

|  获取系统监视参数  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/system/get/param  |
|  请求方式  |  get  |

*   请求参数（application-json）
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  int  |  获取的参数值  |

**更新系统监视参数**

*   接口类型
    

|  更新系统监视参数  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/system/update/param  |
|  请求方式  |  post  |

*   请求参数 -url上拼接
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  param  |  int  |  是  |  监视数据存储时间  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  string  |   |

## 三、报警监视

实时报警列表

**HTTP  POST**

/hsm-smc/v1/alarm/realtime/list

```json
{
  "keyword":"", //模糊匹配
  "alarmLevel":1,
  "pageNum":1,
  "pageSize":10
}
```
```json
{
  "code":0,
  "message":"success",
  "data":{
    "pageNum":1,
    "pageSize":10,
    "total":10,
    "realtimeAlarms":[
      {
        "alarmId":"a.b.c",
        "alarmTime":"2024-03-18 14:32:01",
        "alarmLevel":1,
        "location":"serverA",
        "desc":"create AI execption..."
      }
    ]
  }
}
```

**实时报警导出**

**HTTP  POST**

/hsm-smc/v1/alarm/realtime/export

```json
{
  "keyword":"", //模糊匹配
  "alarmLevel":1,
  "exportSize":1000
}
```

**历史报警列表**

**HTTP  POST**

/hsm-smc/v1/alarm/history/list

```json
{
  "keyword":"", //模糊匹配
  "startTime":"2024-03-01 01:23:55",
  "endTime":"2024-03-19 00:51:01",
  "alarmLevel":1,
  "pageNum":1,
  "pageSize":10
}
```
```json
{
  "code":0,
  "message":"success",
  "data":{
    "pageNum":1,
    "pageSize":10,
    "total":10,
    "realtimeAlarms":[
      {
        "alarmId":"a.b.c",
        "alarmTime":"2024-03-18 14:32:01",
        "onoffStatus":0, //发生恢复状态: 0:off（恢复） 1:on（发生）
        "alarmLevel":1,
        "location":"serverA",
        "desc":"create AI execption..."
      }
    ]
  }
}
```

历史报警导出

**HTTP  POST**

/hsm-smc/v1/alarm/history/export

```json
{
  "keyword":"", //模糊匹配
  "startTime":"2024-03-01 01:23:55",
  "endTime":"2024-03-19 00:51:01",
  "alarmLevel":1,
  "exportSize":1000
}
```

报警备注列表

**HTTP  POST**

/hsm-smc/v1/alarm/note/list

```json
{
  "alarmId":"a.b.c",
  "keyword":"", //模糊匹配
  "startTime":"2024-03-01 01:23:55",
  "endTime":"2024-03-19 00:51:01"
}
```
```json
{
  "code":0,
  "message":"success",
  "data":[
      {
        "alarmId":"a.b.c",
        "noteTime":"2024-03-18 14:32:01",
        "operator": "Bob",
        "noteContent":""
      }
    ]
  
}
```

报警添加备注

**HTTP  POST**

/hsm-smc/v1/alarm/note

```json
请求标头:
Authorzation:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

{
  "alarmId":"a.b.c",
  "noteContent":"execption occurs", 
}
```
```json
{
  "code":0,
  "message":"success",
  "data": nil
  
}
```

## 四、用户管理接口

**创建用户**

*   接口类型
    

|  创建用户  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/user/create  |
|  请求方式  |  post  |

*   请求参数（application-json）
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  userName  |  string  |  是  |   |
|  nickname  |  string  |  否  |   |
|  password  |  string  |  否  |   |
|  role  |  int  |  是  |  0为管理员，1为普通用户  |
|  description  |  string  |  否  |   |
|  status  |  int  |  是  |  默认0  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  string  |  成功返回“创建用户成功”，失败返回错误信息  |

更新用户

*   接口类型
    

|  创建用户  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/user/update  |
|  请求方式  |  post  |

*   请求参数（application-json）
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  id  |  int  |  是  |   |
|  userName  |  string  |  否  |   |
|  nickname  |  string  |  否  |   |
|  password  |  string  |  否  |   |
|  role  |  int  |  是  |  0为管理员，1为普通用户  |
|  description  |  string  |  否  |   |
|  status  |  int  |  是  |  默认0  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  User  |   |

User

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  id  |  int  |   |
|  userName  |  string  |   |
|  nickname  |  string  |   |
|  password  |  string  |   |
|  role  |  int  |   |
|  description  |  string  |   |

登录

*   接口类型
    

|  获取本机ini文件中的应用名称和id  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/user/login  |
|  请求方式  |  post  |

*   请求参数（application-json）
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  userName  |  string  |  是  |   |
|  password  |  string  |  否  |   |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  string  |  token  |

## 五、报警规则

**获取报警规则**

*   接口类型
    

|  获取报警规则数据-分页  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/alarm/rules  |
|  请求方式  |  post  |

*   请求参数（application-json）
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  ruleName  |  string  |  是  |  规则名称-模糊匹配  |
|  pageNum  |  int  |  否  |   |
|  pageSize  |  int  |  否  |   |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  AlarmRuleResp  |   |

AlarmRuleResp

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  pageNum  |  int  |   |
|  pageSize  |  int  |   |
|  total  |  int  |   |
|  alarmRules  |  \[\]AlarmRules  |  报警规则  |

AlarmRules

**停止报警规则（单个或批量）**

*   接口类型
    

|  获取报警规则数据-分页  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/alarm/rule/stop app  |
|  请求方式  |  post  |

*   请求参数（application-json）
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  codes  |  \[\]string  |  是  |  规则code列表，填写一个为单个停用  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  body  |   |

**启动报警规则（单个或批量）**

*   接口类型
    

|  获取报警规则数据-分页  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/alarm/rule/run  |
|  请求方式  |  post  |

*   请求参数（application-json）
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  codes  |  \[\]string  |  是  |  规则code列表，填写一个为单个启用  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  body  |   |

**导出报警规则（csv）**

*   接口类型
    

|   |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/alarm/download/rule  |
|  请求方式  |  Get  |

*   请求参数（url拼接参数）
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |
|  ruleName  |  string  |  否  |  规则名称（模糊）  |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  body  |   |

## 六、拓扑图

**拓扑图列表**

|   |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/node/list  |
|  请求方式  |  Get  |

*   请求参数（url拼接参数）
    

|  参数名  |  数据类型  |  是否必填  |  说明  |
| --- | --- | --- | --- |

*   响应参数
    

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  int  |   |
|  message  |  string  |   |
|  data  |  \[\]NodeResp  |   |

NodeResp

|  属性名  |  类型  |  说明  |
| --- | --- | --- |
|  code  |  string  |  节点编码  |
|  name  |  string  |  节点名称  |
|  isCurrentNode  |  bool  |  是否为当前节点  |
|  ip1  |  string  |  ip1  |
|  ip2  |  string  |  ip2  |
|  isAdmin  |  bool  |  是否是工程师站  |
|  masterNodes  |  \[\]string  |  主节点列表，冗余时，从节点配置，只有一个值1  |
|  groupName  |  string  |  组名称  |
|  alarmLevel  |  int  |  报警级别，0代表没有报警  |

节点导出: 发起导出

**HTTP  POST**

/hsm-smc/v1/topological/data/download?key=UUID

```json
{
  "code":0,
  "message":"success",
  "data": null
}
```

节点导出: 导出结果查询

**HTTP  POST**

/hsm-smc/v1/topological/data/download/result?key=UUID

```json
##正在构建
{
  "code":0,
  "message":"success",
  "data": ""
}
##导出失败
{
  "code":-1,
  "message":"err execption",
  "data": null
}
##导出成功,前端拼接下载路径:
prefix="/hsm-smc/v1/node/download" + "/uuid/node-1711419687.tar.gz"
{
  "code":0,
  "message":"success",
  "data": "/uuid/node-1711419687.tar.gz"
}
```

## 七、监控页面

**节点监控实时表数据**

|  节点监控头部表  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/metric/nodeMonitorTop  |
|  请求方式  |  Get  |
|  请求参数  |  无  |
| ```json {  "code": 0,  "message": "success",  "data": {  "cpu": 20.817514553974522,  "host": "hsm",  "memeTotal": "31335.488",  "memUsedPercent": 45.84788986628455,  "node": "数据服务器"  } } ```  |  |

节点监控历史最早时间

|  节点监控选择历史获取最早时间  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/metric/nodeMonitorEarliest  |
|  请求方式  |  Get  |
|  请求参数  |  无  |
| ```json {  "code": 0,  "message": "success",  "data": 1712364120 } ```  |  |

节点监控导出

|  节点监控导出，条数限制  |  |
| --- | --- |
|  URL  |  /hsm-smc/v1/metric/nodeMonitorExport  |
|  请求方式  |  Get  |
|  请求参数  |  start    数值     秒数时间戳 end     数值     秒数时间戳 limit    数值     导出最大条数  |
|  请求示例  |  /hsm-smc/v1/metric/nodeMonitorExport?start=1712710552&end=1712710652&limit=1000  |
| ```json {  "code": 0,  "message": "success",  "data": "/node/xxx/xxx/xxx.zip" } ```  |  |

节点监控图表数据

|  URL  |  /hsm-smc/v1/metric/nodeMonitorDefault  |
| --- | --- |
|  请求方式  |  Get  |
|  请求参数  |  start    数值     秒数时间戳 end     数值     秒数时间戳  |
|  请求示例  |  /hsm-smc/v1/metric/nodeMonitorDefault?start=1712710552&end=1712710652  |
| ```json {  "code": 0,  "message": "success",  "data": {    "cpu": [      {        "id": 1,        "node": "数据服务器",        "host": "hsm",        "cpuName": "cpu-total",        "usageIdle": 92.28445563371378,        "usageIoWait": 2.28445563371378,        "usageSystem": 3.28445563371378,        "usageUser": 3.28445563371378,        "timestamp": 1712710552      }    ],    "mem": [      {        "id": 1,        "node": "数据服务器",        "host": "hsm",        "total": 31335.49,        "available": 16356.29,        "availablePercent": 52.28445563371378,        "used": 14345.99,        "usedPercent": 36.28445563371378,        "swapTotal": 4716718552,        "timestamp": 1712710552      }    ],    "net": {      "cni0": [        {          "id": 1,          "node": "数据服务器",          "host": "hsm",          "interface": "cni0",          "byteRecv": 17204,          "byteSent": 79387114,          "packetRecv": 306,          "packetSent": 748635,          "timestamp": 1712710552        }      ],      "ens6": [        {          "id": 2,          "node": "数据服务器",          "host": "hsm",          "interface": "ens6",          "byteRecv": 17204,          "byteSent": 79387114,          "packetRecv": 306,          "packetSent": 748635,          "timestamp": 1712710552        }      ]    },    "disk": {      "/": [        {          "id": 1,          "node": "数据服务器",          "host": "hsm",          "path": "/",          "device": "dm-0",          "fstype": "ext4",          "total": 299266.94,          "free": 236794.7,          "used": 47199.04,          "usedPercent": 16.619747764299987,          "timestamp": 1712710552        }      ],      "/boot": [        {          "id": 1,          "node": "数据服务器",          "host": "hsm",          "path": "/boot",          "device": "dm-0",          "fstype": "ext4",          "total": 299266.94,          "free": 236794.7,          "used": 47199.04,          "usedPercent": 16.619747764299987,          "timestamp": 1712710552        }      ]    },    "netCardBandWidth": {      "cni0": [        {          "id": 1,          "node": "数据服务器",          "host": "hsm",          "interface": "cni0",          "recsRate": 0.3,          "sendRate": 1.111,          "timestamp": 1712710552        }      ],      "ens6": [        {          "id": 2,          "node": "数据服务器",          "host": "hsm",          "interface": "ens6",          "recsRate": 0.3,          "sendRate": 1.111,          "timestamp": 1712710552        }      ]    }  } } ```  |  |

应用监控实时表数据

|  URL  |  /hsm-smc/v1/metric/appMonitor  |
| --- | --- |
|  请求方式  |  Get  |
|  请求参数  |  无  |
| ```json {  "code": 0,  "message": "success",  "data": {    "hsm-smc" [      {        "fields": {          "id": "hsm-smc",          "memUsage": 82.54,          "message": "bin/start.sh shell exit with code 0",          "name": "系统管控",          "progs_message": "",          "progs_name": "bin/hsm-smc",          "progs_state": "Ready",          "state": "Started",          "version": "0.9.3"        },        "name": "programStatus",        "tags": {          "dateTime": "2024-04-10 09:22:30",          "host": "hsm",          "node": "数据服务器",          "progs_pid": "935058",          "url": "xxxxx"        },        "timestamp": 1712710552      }    ]  } } ```  |  |

应用监控历史最早时间

|  URL  |  /hsm-smc/v1/metric/appMonitorEarliest/{appId}  |
| --- | --- |
|  请求方式  |  Get   restful请求  |
|  请求参数  |  appId    应用id  |
|  请求示例  |  /hsm-smc/v1/metric/appMonitorEarliest/hsm-smc  |
| ```json {  "code": 0,  "message": "success",  "data": 1712364120 } ```  |  |

应用监控详情

|  URL  |  /hsm-smc/v1/metric/appMonitorDetails  |
| --- | --- |
|  请求方式  |  Get  |
|  请求参数  |  appId    string   应用id start       int       开始时间，秒级时间戳 end        int       开始时间，秒级时间戳  |
|  请求示例  |  /hsm-smc/v1/metric/appMonitorDetails?start=1712710552&end=1712710652&appId=hsm-smc  |
| ```json {  "code": 0,  "message": "success",  "data": {    "processMap": {      "hsm-smc": [        {          "cpu_usage": 2.7995611573078447,          "memory_rss": 77.76,          "memory_usage": 0.7995611573078447,          "memory_vms": 2091.92,          "timestamp": 1712710552        }      ]    },    "processStatus": [      {        "fields": {          "id": "hsm-smc",          "memUsage": 82.54,          "message": "bin/start.sh shell exit with code 0",          "name": "系统管控",          "progs_message": "",          "progs_name": "bin/hsm-smc",          "progs_state": "Ready",          "state": "Started",          "version": "0.9.3"        },        "name": "programStatus",        "tags": {          "dateTime": "2024-04-10 09:22:30",          "host": "hsm",          "node": "数据服务器",          "progs_pid": "935058",          "url": "xxxxx"        }        "timestamp": 1712710552      }    ]  } } ```  |  |

应用监控详情导出

|  URL  |  /hsm-smc/v1/metric/appMonitorDetails  |
| --- | --- |
|  请求方式  |  Get  |
|  请求参数  |  start              数值     秒数时间戳 end               数值     秒数时间戳 limit              数值     导出最大条数 appId           string    应用id appName     string    应用名称  |
|  请求示例  |  /hsm-smc/v1/metric/appMonitorEarliest?start=1712710552&end=1712710652&appId=hsm-smc&appName=hsm-smc  |
| ```json {  "code": 0,  "message": "success",  "data": "/node/xxx/xxx/xxx.zip" } ```  |  |

## 八、登录

用户登录及解锁屏幕

|  URL  |  /hsm-smc/v1/user/login  |
| --- | --- |
|  请求方式  |  Post  |
|  请求头  |  Authorization  token(有值解锁屏幕)  |
|  请求参数  | ```json {   "username": "必填",   "passwd": "必填" } ```  |
|  请求示例  |  /hsm-smc/v1/user/login  |
| ```json {  "code": 0,  "message": "success",  "data": {    "menu": [      {        "id": 1,        "MenuId": "xxx",        "code": "topo",        "name": "拓扑图",        "icon": "",        "url": "http://${currentIp}:6030/#/home",        "helpDocUrl": "",        "urlResource": "",        "i18N": ""      }    ],    "token": "xxx",    "user": {      "account": "omadmin",      "name": "yunweiguanliyuan",      "roleCode": "omadmin"    }  } } ```  |  |

用户注销

|  URL  |  /hsm-smc/v1/user/loginOut  |
| --- | --- |
|  请求方式  |  Get  |
|  请求头  |  Authorization   token Local-Smc         Local-Smc  |
|  请求参数  |  null  |
|  请求示例  |  /hsm-smc/v1/user/loginOut  |
|  1.  http  401      2.  http  200      ```json {  "code": 0,  "message": "注销成功",  "data": null } ``` ```json {  "code": -1,  "message": "注销失败/令牌为空/不是独立系统管控请求",  "data": null } ```  |  |

独立smc访问

|  URL  |  /hsm-smc/v1/\*\*\*  |
| --- | --- |
|  请求方式  |  \*  |
|  请求头  |  Authorization  token（集成和独立都有） Local-Smc        Local-Smc（独立smc要有该值）  |
|  请求示例  |  /hsm-smc/v1/xxx/xxx  |
|  3.  http  401      4.  http  200      ```json {  "code": 0,  "message": "success",  "data": "any" } ``` ```json {  "code": -1,  "message": "password expired",  "data": "" } ```  |  |

token验证

|  URL  |  /hsm-smc/v1/user/valid  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |  Authorization   token Local-Smc        Local-Smc  |
|  请求参数  |   |
|  请求示例  |  /hsm-smc/v1/user/valid  |
| ```json {  "code": 0,  "message": "success",  "data": {    "menu": [      {        "id": 1,        "MenuId": "xxx",        "code": "topo",        "name": "拓扑图",        "icon": "",        "url": "http://${currentIp}:6030/#/home",        "helpDocUrl": "",        "urlResource": "",        "i18N": ""      }    ],    "token": "xxx",    "user": {      "account": "omadmin",      "name": "yunweiguanliyuan",      "roleCode": "omadmin"    }  } } ```  |  |

用户登录及解锁屏幕

|  URL  |  /hsm-smc/v1/user/help  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |  Authorization   token Local-Smc        Local-Smc  |
|  请求参数  |   |
|  请求示例  |  /hsm-smc/v1/user/help  |
| ```json {  "code": 0,  "message": "success",  "data": "http://xxx.xxx.xxx.xxx" } ```  |  |

## 九、部署模式

有无登录页

|  URL  |  /hsm-smc/v1/system/deploy  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |   |
|  请求示例  |  /hsm-smc/v1/system/deploy  |
| ```json {  "code": 0,  "message": "success",  "data": false } ```  |  |

## 十、监视数据上报opm

opm节点历史同步

|  URL  |  /hsm-smc/v1/opm/nodeHistory/:timestamp  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  timestamp  1722423339  |
|  请求示例  |  /hsm-smc/v1/opm/nodeHistory/1722423339  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm节点监控实时数据

|  URL  |  /hsm-smc/v1/opm/nodeMonitorDefault  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  start  1722423339    end  1722423339  |
|  请求示例  |  /hsm-smc/v1/opm/nodeMonitorDefault?start=1722423339&end=1722423339  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm应用历史同步

|  URL  |  /hsm-smc/v1/opm/appHistory/:timestamp  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  timestamp 1722423339  |
|  请求示例  |  /hsm-smc/v1/opm/appHistory/1722423339  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm应用实时表数据

|  URL  |  /hsm-smc/v1/opm/appTable  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |   |
|  请求示例  |  /hsm-smc/v1/opm/appTable  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm应用实时详情

|  URL  |  /hsm-smc/v1/opm/appMonitorDetails  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  start  1722423339    end  1722423339  |
|  请求示例  |  /hsm-smc/v1/opm/appMonitorDetails?start=1722423339&end=1722423339  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm操作日志同步

|  URL  |  /hsm-smc/v1/opm/optLog/:timestamp  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  timestamp 1722423339  |
|  请求示例  |  /hsm-smc/v1/opm/optLog/1722423339  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm节点监控实时表

|  URL  |  /hsm-smc/v1/opm/nodeMonitorTop  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |   |
|  请求示例  |  /hsm-smc/v1/opm/nodeMonitorTop  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm应用详情实时导出

|  URL  |  /hsm-smc/v1/opm/appMonitorExport  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  start              数值     秒数时间戳 end               数值     秒数时间戳 limit              数值     导出最大条数 appId           string    应用id appName     string    应用名称  |
|  请求示例  |  /hsm-smc/v1/opm/appMonitorExport?start=1712710552&end=1712710652&appId=hsm-smc&appName=hsm-smc&limit=1000  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm节点监控实时导出

|  URL  |  /hsm-smc/v1/opm/nodeMonitorExport  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  start              数值     秒数时间戳 end               数值     秒数时间戳 limit              数值     导出最大条数  |
|  请求示例  |  /hsm-smc/v1/opm/nodeMonitorExport?start=1712710552&end=1712710652&limit=1000  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm节点总览

|  URL  |  /hsm-smc/v1/opm/overview/:start/:end  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  start              数值     秒数时间戳 end               数值     秒数时间戳  |
|  请求示例  |  /hsm-smc/v1/opm/overview/1712710552/1712710652  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm启动应用

|  URL  |  /hsm-smc/v1/opm/start  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  id  |
|  请求示例  |  /hsm-smc/v1/opm/start?id=hsm-smc  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm停止应用

|  URL  |  /hsm-smc/v1/opm/stop  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  id  |
|  请求示例  |  /hsm-smc/v1/opm/stop?id=hsm-smc  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm重启应用

|  URL  |  /hsm-smc/v1/opm/restart  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  id  |
|  请求示例  |  /hsm-smc/v1/opm/restart?id=hsm-smc  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm触发smc进行logs发送

|  URL  |  /hsm-smc/v1/opm/sendLog  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  period 周期/秒  |
|  请求示例  |  /hsm-smc/v1/opm/sendLog?period=10  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm设置smc登录页配置

|  URL  |  /hsm-smc/v1/opm/set/loginPageSetting  |
| --- | --- |
|  请求方式  |  post  |
|  请求头  |   |
|  请求参数  | ```json {   "hello":"xxx",   "logo":"xxx",   "loginTitle":"xxx",   "backgroundImage":"xxx" } ```  |
|  请求示例  |  /hsm-smc/v1/opm/set/loginPageSetting  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm设置smc产品页配置

|  URL  |  /hsm-smc/v1/opm/set/productSetting  |
| --- | --- |
|  请求方式  |  post  |
|  请求头  |   |
|  请求参数  | ```json {   "productName":"xxx",   "productLogo":"xxx",   "browserTitle":"xxx",   "browserLogo":"xxx" } ```  |
|  请求示例  |  /hsm-smc/v1/opm/set/productSetting  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm获取应用日志参数配置

|  URL  |  /hsm-smc/v1/opm/get/appLogConf  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |   |
|  请求示例  |  /hsm-smc/v1/opm/get/appLogConf  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm修改应用日志参数配置

|  URL  |  /hsm-smc/v1/opm/set/appLogConf  |
| --- | --- |
|  请求方式  |  post  |
|  请求头  |   |
|  请求参数  | ```json {   "node":"s1",   "node_name":"xxx",   "app_id":"xxx",   "app_name":"xxx",   "log_update_mode":"xxx",   "log_query_config": "xxx",   "log_update_notify":"xxx",   "log_level":"xxx",   "log_limimt_sign":"xxx",   "log_max_number":10,   "log_max_storage_time":10 } ```  |
|  请求示例  |  /hsm-smc/v1/system/set/appLogConf  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

opm导出应用日志参数配置

|  URL  |  /hsm-smc/v1/opm/export/appLogConf/:search  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |  search   查询参数    无时值为null  |
|  请求示例  |  /hsm-smc/v1/system/export/appLogConf/null  |
| ```json {   "node":"s1",   "node_name":"xxx",   "app_id":"xxx",   "app_name":"xxx",   "log_update_mode":"xxx",   "log_query_config": "xxx",   "log_update_notify":"xxx",   "log_level":"xxx",   "log_limimt_sign":"xxx",   "log_max_number":10,   "log_max_storage_time":10 } ```  |  |

## 十一、系统配置

获取应用日志参数配置

|  URL  |  /hsm-smc/v1/system/get/appLogConf  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |  Authorization   token Local-Smc         Local-Smc  |
|  请求参数  |   |
|  请求示例  |  /hsm-smc/v1/system/get/appLogConf  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

修改应用日志参数配置

|  URL  |  /hsm-smc/v1/system/set/appLogConf  |
| --- | --- |
|  请求方式  |  post  |
|  请求头  |  Authorization   token Local-Smc         Local-Smc  |
|  请求参数  | ```json {   "node":"s1",   "node_name":"xxx",   "app_id":"xxx",   "app_name":"xxx",   "log_update_mode":"xxx",   "log_query_config": "xxx",   "log_update_notify":"xxx",   "log_level":"xxx",   "log_limimt_sign":"xxx",   "log_max_number":10,   "log_max_storage_time":10 } ```  |
|  请求示例  |  /hsm-smc/v1/system/set/appLogConf  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

导出应用日志参数配置

|  URL  |  /hsm-smc/v1/system/export/appLogConf/:search  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |  Authorization   token Local-Smc         Local-Smc  |
|  请求参数  |  search   查询参数    无时值为null  |
|  请求示例  |  /hsm-smc/v1/system/export/appLogConf/null  |
| ```json {   "node":"s1",   "node_name":"xxx",   "app_id":"xxx",   "app_name":"xxx",   "log_update_mode":"xxx",   "log_query_config": "xxx",   "log_update_notify":"xxx",   "log_level":"xxx",   "log_limimt_sign":"xxx",   "log_max_number":10,   "log_max_storage_time":10 } ```  |  |

获取应用环境变量配置

|  URL  |  /hsm-smc/v1/system/get/appEnvs  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |  Authorization   token Local-Smc         Local-Smc  |
|  请求参数  |   |
|  请求示例  |  /hsm-smc/v1/system/get/appEnvs  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

设置应用环境变量配置

|  URL  |  /hsm-smc/v1/system/set/appEnvs  |
| --- | --- |
|  请求方式  |  post  |
|  请求头  |  Authorization   token Local-Smc         Local-Smc  |
|  请求参数  | ```json {   "node":"s1",   "nodeName":"xxx",   "appId":"xxx",   "appName":"xxx",   "envs":[{"xxx":"xxx"}] } ```  |
|  请求示例  |  /hsm-smc/v1/system/set/appEnvs  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

生效应用环境变量配置

|  URL  |  /hsm-smc/v1/system/run/appEnvs  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |  Authorization   token Local-Smc         Local-Smc  |
|  请求参数  |  appId  应用id   appName  应用名称  |
|  请求示例  |  /hsm-smc/v1/system/run/appEnvs?appId=hsm-smc&appName=系统管控  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

三方应用通知修改环境变量配置

|  URL  |  /hsm-smc/v1/provided/appEnvs/notify  |
| --- | --- |
|  请求方式  |  post  |
|  请求头  |   |
|  请求参数  | ```json {   "appId": "hsm-smc",   "appName": "系统管控",//非必填项   "envs": [     {       "envKey": "xxx",       "envValue": "xxx"     }   ] } ```  |
|  请求示例  |  /hsm-smc/v1/provided/appEnvs/notify  |
|  备注：因为环境变量修改后会直接通知sk去生效，可能通知应用来不及接收返回就被重启 ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

获取登录页配置

|  URL  |  /hsm-smc/v1/system/get/loginPageSetting  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |   |
|  请求示例  |  /hsm-smc/v1/system/get/loginPageSetting  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

获取产品页配置

|  URL  |  /hsm-smc/v1/system/get/productSetting  |
| --- | --- |
|  请求方式  |  get  |
|  请求头  |   |
|  请求参数  |   |
|  请求示例  |  /hsm-smc/v1/system/get/productSetting  |
| ```json {  "code": 0,  "message": "success",  "data": xxx } ```  |  |

## 12、备份恢复

查看当前可备份数据类型

**HTTP  GET**

/hsm-smc/v1/topological/backup/dataTypes

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data":[
    "runData",
    "pgSql",
    "influxdb",
    "runLog",
  ]
  
}
```

触发备份任务

**HTTP  GET**

/hsm-smc/v1/topological/backup?dataTypes=runData,pgSql,influxdb,runLog

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data": null
}
```

查看备份记录

**HTTP  POST**

/hsm-smc/v1/topological/backup/listRecord

```json
{
  "pageNum":1,
  "pageSize":10,
  "startTime":1717998705,
  "endTime":1718247930,
  "keyword":"",
  "status":0, //1-进行中 2-终止中 3-终止 4-失败 5-成功 0/不传-全部
}
```
```json
{
  "code":0,
  "message":"success",
  "data": {
    "pageNum":1,
    "pageSize":10,
    "total":5,
    "backupRecords":[
      {
        "id":1,
        "taskId":"s1_backup_20240624133820",
        "executeTime":time,
        "location":"/var/opt/hsm-os/backup",
        "mode":2, //备份方式：1-自动备份 2-手动备份
        "type":1, //备份类型：1-节点备份 2-系统备份
        "content":"运行环境、postgresql数据库、influxdb数据库、运行数据、日志数据",
        "strategy":1, //备份策略：1-全量备份 2-增量备份
        "rate":1,//备份频率 1次/天
        "status":1, //备份状态：1-进行中 2-终止中 3-终止 4-失败 5-成功
        "errmsg":"错误描述",
        "progress":20,//进度
        "flag":false //能否下载 true-可下载 false-不能下载
        
      }
      
    ]
  }
}
```

备份终止

**HTTP  GET**

/hsm-smc/v1/topological/backup/cancel?taskId=s1\_backup\_20240624133820

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data": null
}
```

备份下载

**HTTP  GET**

/hsm-smc/v1/topological/backup/download?taskId=s1\_backup\_20240624133820

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data": "s1_backup_20240624133820.tar.gz"
  //根据上述返回值，拼接完整路径: http://127.0.0.1:6030/hsm-smc/v1/node/download/s1_backup_20240624133820.tar.gz 下载
}
```

节点恢复-查看可恢复的备份记录taskId列表

**HTTP  GET**

/hsm-smc/v1/topological/backup/listValidTaskIds

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data": [
    "s1_backup_20240624133820"
  ]
}
```

节点恢复-查看备份应用列表

**HTTP  GET**

/hsm-smc/v1/topological/backup/listService?backupTaskId=s1\_backup\_20240624133820&keyword=x

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data": {
    "taskId":"s1_backup_20240624133820",
    "services":[
      {
        "appId":"hsm-io-it",
        "appName":"xxx"，
        "appVersion":"0.10.4",
        "appType":"legacy", //legacy-传统应用 container-容器应用
        "runDataFlag":true,
        "runLogFlag":true
      }
    ]
  }
}
```

节点恢复-触发恢复运行数据

**HTTP  POST**

/hsm-smc/v1/topological/recover/runData

```json
{
  "backupTaskId":"s1_backup_20240624133820",
  "services":[
    {
      "appId":"hsm-io-it",
      "version":"0.10.4"
    }
  ]
}
```
```json
{
  "code":0,
  "message":"success",
  "data": null
}
```

节点恢复-触发恢复运行日志

**HTTP  POST**

/hsm-smc/v1/topological/recover/runLog

```json
{
  "backupTaskId":"s1_backup_20240624133820",
  "services":[
    {
      "appId":"hsm-io-it",
      "version":"0.10.4"
    }
  ]
}
```
```json
{
  "code":0,
  "message":"success",
  "data": null
}
```

节点恢复-查看恢复记录

**HTTP  POST**

/hsm-smc/v1/topological/recover/listRecord

```json
{
  "pageNum":1,
  "pageSize":10,
  "startTime":1717998705,
  "endTime":1718247930,
  "keyword":"",
  "status":0, //1-进行中 2-终止中 3-终止 4-失败 5-成功 0/不传-全部
}
```
```json
{
  "code":0,
  "message":"success",
  "data": {
    "pageNum":1,
    "pageSize":10,
    "total":5,
    "backupRecords":[
      {
        "id":1,
        "taskId":"s1_20240624133820",
        "backupTaskId":"s1_backup_20240624133820",
        "executeTime":time,
        "backupLocation":"/var/opt/hsm-os/backup",
        "content":"运行环境、postgresql数据库、influxdb数据库、运行数据、日志数据",
        "type":1, //恢复类型：1-恢复 2-克隆
        "ip":"172.21.44.89",//目标ip
        "strategy":1, //恢复策略： 1-覆盖恢复 2-增量恢复
        "status":1, //恢复状态：1-进行中 2-终止中 3-终止 4-失败 5-成功
        "errmsg":"错误描述",
        "progress":20//进度
      }
      
    ]
  }
}
```

节点克隆-检查ip

**HTTP  GET**

/hsm-smc/v1/topological/clone/checkIp?ip1=172.21.44.xx&ip2=172.21.44.xx

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data": ""  // ""-检查通过 sk-普通服务器需要安装sk setup-工程师站需要安装最小化环境
}
```

节点克隆-获取下载链接

**HTTP  GET**

/hsm-smc/v1/topological/clone/download?backupTaskId=s1\_backup\_20240624133820&type=sk

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data": "sk_s1_backup_20240624133820.tar.gz"  
  // type=sk 返回："sk_s1_backup_20240624133820.tar.gz"
  // type=setup 返回："setup_sk_s1_backup_20240624133820.tar.gz"
  //根据上述返回值，拼接完整路径: http://127.0.0.1:6030/hsm-smc/v1/node/download/sk_s1_backup_20240624133820.tar.gz 下载
}
```

节点克隆-查看当前可恢复数据类型

**HTTP  GET**

/hsm-smc/v1/topological/clone/dataTypes?backupTaskId=s1\_backup\_20240624133820

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data":[
    "runData",
    "pgSql",
    "influxdb",
    "runLog",
  ]
  
}
```

节点克隆-触发克隆任务

**HTTP  GET**

/hsm-smc/v1/topological/clone/node?backupTaskId=s1\_backup\_20240624133820&ip1=172.21.44.x&ip2=172.21.44.x&dataTypes=runData,pgSql,influxdb,runLog

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data":null
}
```

节点克隆-查看克隆记录

同 9.节点恢复-查看恢复记录

节点克隆-克隆终止

**HTTP  GET**

/hsm-smc/v1/topological/clone/cancel?taskId=s1\_20240624133820

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data":null
}
```

节点备份设置-查看配置

**HTTP  GET**

/hsm-smc/v1/topological/backup/config

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data":{
    "id":1,
    "auto":0,  //自动备份开关 0-开 1-关
    "rate":1, //备份频率
    "location":"/var/opt/hsm-os/backup/",
    "manualKeepNum":1, //手动备份保留条数
    "autoKeepNum":1 //自动备份保留条数
  }
}
```

节点备份设置-选择备份路径-查看目录

**HTTP  GET**

/hsm-smc/v1/topological/dir/tree?path=/

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data":[
    "home",
    "opt",
    "var"
  ]
}
```

节点备份设置-选择备份路径-创建目录

**HTTP  GET**

/hsm-smc/v1/topological/dir/create?path=/var/opt/hsm-os/backup

```json
无
```
```json
{
  "code":0,
  "message":"success",
  "data":null
}
```

节点备份设置-保存配置

**HTTP  POST**

/hsm-smc/v1/topological/backup/config/save

```json
{
  "id":1,
  "auto":0,  //自动备份开关 0-开 1-关
  "rate":1, //备份频率
  "location":"/var/opt/hsm-os/backup/",
  "manualKeepNum":1, //手动备份保留条数
  "autoKeepNum":1 //自动备份保留条数
}
```
```json
{
  "code":0,
  "message":"success",
  "data":null
}
```
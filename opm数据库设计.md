# opm数据库设计

#### 1.基础数据

包含：基础数据，配置数据等，如应用监视列表、操作日志、运维配置等。使用sqlite存储

##### 1.1应用监视列表 (app\_status)

|  字段ID  |  字段名称  |  类型  |  是否主键  |  描述  |
| --- | --- | --- | --- | --- |
|  id  |  主键Id  |  INTEGER  |  是  |   |
|  node  |  节点名称  |  TEXT  |   |  节点配置文件-节点名称  |
|  host  |  主机名  |  TEXT  |   |  主机hostname  |
|  app\_id  |  应用id  |  TEXT  |   |   |
|  app\_name  |  应用名称  |  TEXT  |   |   |
|  version  |  应用版本  |  TEXT  |   |   |
|  app\_state  |  应用状态  |  TEXT  |   |   |
|  process\_pid  |  进程id  |  TEXT  |   |   |
|  process\_name  |  进程名称  |  TEXT  |   |   |
|  process\_state  |  进程状态  |  TEXT  |   |   |
|  process\_message  |  进程信息  |  TEXT  |   |   |
|  timestamp  |  采集时间  |  timestamp  |   |   |

##### 1.2操作日志表(opm\_oplog)

|  字段ID  |  字段名称  |  类型  |  是否主键  |  描述  |
| --- | --- | --- | --- | --- |
|  id  |  主键Id  |  INTEGER  |  是  |   |
|  node  |  节点名称  |  TEXT  |   |  节点配置文件-节点名称  |
|  host  |  主机名  |  TEXT  |   |  主机hostname  |
|  op\_time  |  操作时间  |  DATETIME  |   |   |
|  operator  |  操作人  |  TEXT  |   |   |
|  op\_obj  |  操作对象  |  TEXT  |   |  对应用进行启停操作时，设置为被操作应用名称  |
|  location  |  操作位置  |  TEXT  |   |   |
|  desc  |  操作描述  |  TEXT  |   |   |
|  from\_sk  |  是否是来自SK  |  BOOL  |   |   |
|  extend\[1:5\]  |   |  TEXT  |   |   |

##### 1.3历史报警监视数据

|  字段ID  |  字段名称  |  类型  |  是否主键  |  描述  |
| --- | --- | --- | --- | --- |
|  id  |  主键Id  |  int  |  是  |   |
|  alarmId  |  报警Id  |  string  |   |   |
|  alarmTime  |  报警时间  |  time  |   |   |
|  alarmLevel  |  报警级别  |  int  |   |   |
|  alarmType  |  报警类型  |  int  |   |   |
|  onoffstatus  |  报警状态  |  int  |   |   |
|  location  |  操作位置  |  string  |   |   |
|  desc  |  操作描述  |  string  |   |   |
|  ruleCode  |  报警规则code  |  string  |   |   |
|  alarmTimeInt  |  报警时间戳  |  int  |   |   |

##### 1.4 报警备注

|  字段ID  |  字段名称  |  类型  |  是否主键  |  描述  |
| --- | --- | --- | --- | --- |
|  id  |  主键Id  |  int  |  是  |   |
|  alarmId  |  报警Id  |  string  |   |   |
|  noteTime  |  备注时间  |  time  |   |   |
|  operator  |  操作人  |  string  |   |   |
|  noteContent  |  备注内容  |  string  |   |   |
|  noteCode  |  备注编码  |  string  |   |   |
|  noteTimeInt  |  备注时间戳  |  string  |   |   |
|  alarmTimeInt  |  报警时间戳  |  int  |   |   |

##### 1.5报警规则

|  id  |  主键Id  |  int  |  是  |   |
| --- | --- | --- | --- | --- |
|  code  |  规则编码  |  string  |   |   |
|  isDefault  |  是否默认  |  bool  |   |   |
|  version  |  版本  |  string  |   |   |
|  ruleName  |  规则名称  |  string  |   |   |
|  ruleType  |  规则类型  |  string  |   |   |
|  ruleContent  |  规则内容  |  string  |   |   |
|  ruleObj  |  规则对象  |  string  |   |   |
|  condition1  |  报警规则1  |  string  |   |   |
|  condition2  |  报警规则2  |  int  |   |   |
|  enabled  |  是否启用  |  bool  |   |   |
|  extend\[1:5\]  |   |  TEXT  |   |   |

##### 1.6 系统设置表

|  id  |  主键Id  |  int  |  是  |   |
| --- | --- | --- | --- | --- |
|  monitorType  |  系统设置类型  |  string  |   |   |
|  value  |  值  |  int  |   |   |

######   
2.时序数据

包含：节点、应用进程采集数据指标的时序数据。使用influxdb存储。

存储格式

![图片 4](https://alidocs.oss-cn-zhangjiakou.aliyuncs.com/res/meonaArMPP7rnXxj/img/bf39a34e-d006-45c8-91da-e5743bd71eec.png)

默认org=opm  bucket=smc

##### 2.1 节点数据

###### 2.1.1 cpu

measurement=node\_cpu

|  类型  |  名称  |  k类型（string）  |  v类型  |
| --- | --- | --- | --- |
|  node  |  节点名称  |  tag\_key  |  string  |
|  host  |  主机名  |  tag\_key  |  string  |
|  cpu\_name  |  逻辑cpu名称  |  tag\_key  |  string  |
|  usage\_idle  |  cpu空闲率  |  field  |  float64  |
|  usage\_iowait  |  io等待率  |  field  |  float64  |
|  usage\_system  |  系统使用cpu占比  |  field  |  float64  |
|  usage\_user  |  用户使用cpu占比  |  field  |  float64  |
|  time  |  采集时间  |   |  时间戳  |

示例：node\_cpu 

|node=”数据服务器”,  host=”hsm”,  cpu\_name=”cpu-total” 

| usage\_idle=88.665, usage\_iowait=0.896,  usage\_system=4.481,  usage\_user=5.314

|time=1712795400

###### 2.1.2 内存

measurement=node\_memory

|  类型  |  名称  |  k类型（string）  |  v类型  |
| --- | --- | --- | --- |
|  node  |  节点名称  |  tag\_key  |  string  |
|  host  |  主机名  |  tag\_key  |  string  |
|  total  |  内存总量  |  field  |  float64  |
|  available  |  内存可用量  |  field  |  float64  |
|  available\_percent  |  内存可用率  |  field  |  float64  |
|  used  |  内存已用量  |  field  |  float64  |
|  used\_percent  |  内存已用占比  |  field  |  float64  |
|  swap\_total  |  交换内存大小  |  field  |  float64  |
|  time  |  采集时间  |   |  时间戳  |

###### 2.1.3 网络

measurement=node\_net

|  类型  |  名称  |  k类型（string）  |  v类型  |
| --- | --- | --- | --- |
|  node  |  节点名称  |  tag\_key  |  string  |
|  host  |  主机名  |  tag\_key  |  string  |
|  interface  |  网卡名  |  tag\_key  |  string  |
|  bytes\_sent  |  发送字节数  |  field  |  float64  |
|  bytes\_recv  |  接收字节数  |  field  |  float64  |
|  packets\_sent  |  发送数据包数  |  field  |  float64  |
|  packets\_recv  |  接收数据包数  |  field  |  float64  |
|  time  |  采集时间  |   |  时间戳  |

###### 2.1.4 磁盘

measurement=node\_disk

|  类型  |  名称  |  k类型（string）  |  v类型  |
| --- | --- | --- | --- |
|  node  |  节点名称  |  tag\_key  |  string  |
|  host  |  主机名  |  tag\_key  |  string  |
|  path  |  挂载点  |  tag\_key  |  string  |
|  device  |  设备类型  |  tag\_key  |  string  |
|  fstype  |  文件系统类型  |  tag\_key  |  string  |
|  total  |  挂载点总容量  |  field  |  float64  |
|  free  |  挂载点剩余容量  |  field  |  float64  |
|  used  |  挂载点已用容量  |  field  |  float64  |
|  used\_percent  |  已用占比  |  field  |  float64  |
|  time  |  采集时间  |   |  时间戳  |

###### C2.1.5 带宽

measurement=node\_netCardBandwidth

|  类型  |  名称  |  k类型（string）  |  v类型  |
| --- | --- | --- | --- |
|  node  |  节点名称  |  tag\_key  |  string  |
|  host  |  主机名  |  tag\_key  |  string  |
|  interface  |  网卡名  |  tag\_key  |  string  |
|  sent\_rate  |  发送比率  |  field  |  float64  |
|  recv\_rate  |  接收比率  |  field  |  float64  |
|  time  |  采集时间  |   |  时间戳  |

##### 2.2 应用数据

###### 2.2.1 cpu/内存利用率以及使用量

![图片 1](https://alidocs.oss-cn-zhangjiakou.aliyuncs.com/res/meonaArMPP7rnXxj/img/069937a9-cee5-4023-b7c4-a402654fbb17.png)

measurement=app\_stat

|  类型  |  名称  |  k类型（string）  |  v类型  |
| --- | --- | --- | --- |
|  node  |  节点名称  |  tag\_key  |  string  |
|  host  |  主机名  |  tag\_key  |  string  |
|  user  |  启动用户  |  tag\_key  |  string  |
|  pid  |  进程id  |  tag\_key  |  string  |
|  app\_id  |  应用id  |  tag\_key  |  string  |
|  app\_name  |  应用名称  |  tag\_key  |  string  |
|  process\_Id  |  程序id  |  tag\_key  |  string  |
|  pid\_file  |  pidfile路径  |  tag\_key  |  string  |
|  cmd\_line  |  执行命令  |  tag\_key  |  string  |
|  cpu\_usage  |  cpu使用率  |  field  |  float64  |
|  memory\_usage  |  内存使用率  |  field  |  float64  |
|  memory\_rss  |  实存使用量  |  field  |  float64  |
|  memory\_vms  |  虚存使用量  |  field  |  float64  |
|  time  |  采集时间  |   |  时间戳  |

系统数据

##### 2.1登录配置

|  表名：login\_page\_setting  |  |  |  |  |
| --- | --- | --- | --- | --- |
|  字段ID  |  字段名称  |  类型  |  是否主键  |  描述  |
|  id  |  主键Id  |  int  |  是  |   |
|  app\_id  |  应用id  |  string  |   |   |
|  hello  |  登录欢迎  |  string  |   |   |
|  logo  |  登录logo  |  string  |   |   |
|  login\_title  |  登录标题  |  string  |   |   |
|  background\_image  |  登录背景  |  string  |   |   |
|  create\_at  |  创建时间  |  time  |   |   |
|  update\_at  |  更新时间  |  time  |   |   |

|  表名：product\_setting  |  |  |  |  |
| --- | --- | --- | --- | --- |
|  字段ID  |  字段名称  |  类型  |  是否主键  |  描述  |
|  id  |  主键Id  |  int  |  是  |   |
|  app\_id  |  应用id  |  string  |   |   |
|  product\_name  |  产品名称  |  string  |   |   |
|  product\_logo  |  产品logo  |  string  |   |   |
|  browser\_title  |  浏览器标题  |  string  |   |   |
|  browser\_logo  |  浏览器logo  |  string  |   |   |
|  create\_at  |  创建时间  |  time  |   |   |
|  update\_at  |  更新时间  |  time  |   |   |

##### 2.2用户配置

|  表名：auth\_user  |  |  |  |  |
| --- | --- | --- | --- | --- |
|  字段ID  |  字段名称  |  类型  |  是否主键  |  描述  |
|  id  |  主键Id  |  int  |  是  |   |
|  account  |  账号  |  string  |   |   |
|  name  |  名称  |  string  |   |   |
|  pass\_wd  |  密码  |  string  |   |   |
|  state  |  状态  |  int  |   |  1-正常 2-锁定 3-过期  |
|  is\_delete  |  是否删除  |  int  |   |  0-未删除 1-删除  |
|  role\_id  |  角色id  |  string  |   |   |
|  role\_code  |  角色编号  |  string  |   |   |
|  role\_name  |  角色名称  |  string  |   |   |
|  create\_at  |  创建时间  |  time  |   |   |
|  update\_at  |  更新时间  |  time  |   |   |

|  表名：auth\_role\_permission  |  |  |  |  |
| --- | --- | --- | --- | --- |
|  字段ID  |  字段名称  |  类型  |  是否主键  |  描述  |
|  id  |  主键Id  |  int  |  是  |   |
|  meun\_id  |  菜单id  |  string  |   |   |
|  status  |  状态  |  bool  |   |  true-可用 false-不可用  |
|  code  |  菜单code  |  string  |   |   |
|  name  |  菜单名称  |  string  |   |   |
|  icon  |  图标  |  string  |   |   |
|  menu\_type  |  菜单类型  |  int  |   |  1-内置直接打开 2-内嵌 3-新窗口打开  |
|  url  |  地址  |  string  |   |   |
|  help\_doc\_url  |  帮助文档地址  |  string  |   |   |
|  url\_resource  |  资源地址  |  string  |   |   |
|  i18n  |  国际化  |  string  |   |   |
|  role\_code  |  角色编号  |  string  |   |   |
|  role\_name  |  角色名称  |  string  |   |   |
|  create\_at  |  创建时间  |  time  |   |   |
|  update\_at  |  更新时间  |  time  |   |   |
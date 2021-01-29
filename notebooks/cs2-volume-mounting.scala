// Databricks notebook source
val configs = Map(
  "fs.azure.account.auth.type" -> "OAuth",
  "fs.azure.account.oauth.provider.type" -> "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider",
  "fs.azure.account.oauth2.client.id" -> "e4edd8c1-2026-4e42-a8bf-bdbcc02df168",
  "fs.azure.account.oauth2.client.secret" -> dbutils.secrets.get(scope = "training-scope", key = "appsecret"),
  "fs.azure.account.oauth2.client.endpoint" -> "https://login.microsoftonline.com/f162b8ca-3a8c-4d22-a509-3a0f10dd3695/oauth2/token")

// COMMAND ----------

import scala.util.control._

// COMMAND ----------

var mounts=dbutils.fs.mounts()
var mountPath="/mnt/data"
var isExist=false

val outer = new Breaks;

outer.breakable {
  for(mount <- mounts) {
    if(mount.mountPoint == mountPath) {
      isExist = true;
      outer.break;
    }
  }
}


// COMMAND ----------

if(isExist) {
  println("Volume Mounting for Case Study Data Already Exist!")
}
else {
  dbutils.fs.mount(
    source = "abfss://casestudydata@iomegadls.dfs.core.windows.net/",
    mountPoint = "/mnt/data",
    extraConfigs = configs)
}


// COMMAND ----------


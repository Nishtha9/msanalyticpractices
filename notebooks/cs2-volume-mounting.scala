// Databricks notebook source
val configs = Map(
  "fs.azure.account.auth.type" -> "OAuth",
  "fs.azure.account.oauth.provider.type" -> "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider",
  "fs.azure.account.oauth2.client.id" -> "f84616aa-d6be-478e-b2fc-9ef7c9ca078b",
  "fs.azure.account.oauth2.client.secret" -> dbutils.secrets.get(scope = "training-scope", key = "appsecret"),
  "fs.azure.account.oauth2.client.endpoint" -> "https://login.microsoftonline.com/381a10df-8e85-43db-86e1-8893b075b027/oauth2/token")

// COMMAND ----------

val mounts=dbutils.fs.mounts()
val mountPath="/mnt/data"
val isExist=false

for (mount <- mounts){
  if (mount.mountPoint == mountPath){
    isExist = true
    break
  }
}



// COMMAND ----------

println("Exist status "+ isExist)
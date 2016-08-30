package com.secneo.apkwrapper;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.ArrayMap;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class ApplicationWrapper
  extends Application
{
  public static Application realApplication = null;
  
  static
  {
    System.loadLibrary("DexHelper");
    if (Helper.PPATH != null) {
      System.load(Helper.PPATH);
    }
  }
  
  protected void attachBaseContext(Context paramContext)
  {
    super.attachBaseContext(paramContext);
    try
    {
      realApplication = (Application)getClassLoader().loadClass(Helper.APPNAME).newInstance();
      Helper.attach(realApplication, paramContext);
      return;
    }
    catch (Exception localException)
    {
      realApplication = null;
    }
  }
  
  public void huawei_share()
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException
  {
    if (!Build.BRAND.equalsIgnoreCase("Huawei")) {}
    for (;;)
    {
      return;
      Class localClass1 = Class.forName("android.app.ActivityThread");
      Object localObject1 = localClass1.getDeclaredMethod("currentActivityThread", new Class[0]).invoke(localClass1, new Object[0]);
      Object localObject2 = localClass1.getDeclaredMethod("getSystemContext", new Class[0]).invoke(localObject1, new Object[0]);
      Field localField1 = Class.forName("android.app.ContextImpl").getDeclaredField("sSharedPrefs");
      localField1.setAccessible(true);
      Object localObject3 = localField1.get(localObject2);
      if ((!localObject3.getClass().toString().contains("HashMap")) && (localObject3 != null) && (!"{}".endsWith(localObject3.toString())))
      {
        Iterator localIterator = ((ArrayMap)((ArrayMap)localObject3).get(Helper.PKGNAME)).entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          localEntry.getKey();
          Object localObject4 = localEntry.getValue();
          Class localClass2 = localObject4.getClass();
          Field localField2 = localClass2.getDeclaredField("mMode");
          localField2.setAccessible(true);
          localField2.get(localObject4);
          Boolean.valueOf(false);
          Method localMethod = localClass2.getDeclaredMethod("startLoadFromDisk", new Class[0]);
          localMethod.setAccessible(true);
          localMethod.invoke(localObject4, new Object[0]);
        }
      }
    }
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if (realApplication != null) {
      realApplication.onConfigurationChanged(paramConfiguration);
    }
  }
  
  public void onCreate()
  {
    super.onCreate();
    try
    {
      huawei_share();
      if (realApplication != null)
      {
        Helper.attach(realApplication, null);
        realApplication.onCreate();
      }
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  public void onLowMemory()
  {
    super.onLowMemory();
    if (realApplication != null) {
      realApplication.onLowMemory();
    }
  }
  
  public void onTerminate()
  {
    super.onTerminate();
    if (realApplication != null) {
      realApplication.onTerminate();
    }
  }
  
  public void onTrimMemory(int paramInt)
  {
    try
    {
      super.onTrimMemory(paramInt);
      if (realApplication != null) {
        realApplication.onTrimMemory(paramInt);
      }
      return;
    }
    catch (Exception localException) {}
  }
}


/* Location:              D:\project\semiHumanoidRobot\unzip APK tool\cn.ninebot.ninebot_dex2jar.jar!\com\secneo\apkwrapper\ApplicationWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
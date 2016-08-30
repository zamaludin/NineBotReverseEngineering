package com.secneo.apkwrapper;

import android.os.Build.VERSION;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.ZipFile;

public class DexInstall
{
  private static void expandFieldArray(Object paramObject, String paramString, Object[] paramArrayOfObject)
    throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
  {
    Field localField = findField(paramObject, paramString);
    Object[] arrayOfObject1 = (Object[])localField.get(paramObject);
    Object[] arrayOfObject2 = (Object[])Array.newInstance(arrayOfObject1.getClass().getComponentType(), arrayOfObject1.length + paramArrayOfObject.length);
    System.arraycopy(arrayOfObject1, 0, arrayOfObject2, paramArrayOfObject.length, arrayOfObject1.length);
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject2, 0, paramArrayOfObject.length);
    localField.set(paramObject, arrayOfObject2);
  }
  
  private static Field findField(Object paramObject, String paramString)
    throws NoSuchFieldException
  {
    Class localClass = paramObject.getClass();
    while (localClass != null) {
      try
      {
        Field localField = localClass.getDeclaredField(paramString);
        if (!localField.isAccessible()) {
          localField.setAccessible(true);
        }
        return localField;
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        localClass = localClass.getSuperclass();
      }
    }
    throw new NoSuchFieldException("Field " + paramString + " not found in " + paramObject.getClass());
  }
  
  private static Method findMethod(Object paramObject, String paramString, Class<?>... paramVarArgs)
    throws NoSuchMethodException
  {
    Class localClass = paramObject.getClass();
    while (localClass != null) {
      try
      {
        Method localMethod = localClass.getDeclaredMethod(paramString, paramVarArgs);
        if (!localMethod.isAccessible()) {
          localMethod.setAccessible(true);
        }
        return localMethod;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        localClass = localClass.getSuperclass();
      }
    }
    throw new NoSuchMethodException("Method " + paramString + " with parameters " + Arrays.asList(paramVarArgs) + " not found in " + paramObject.getClass());
  }
  
  public static void install(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      File localFile = new File(paramString);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(localFile);
      installSecondaryDexes(paramClassLoader, localFile.getParentFile(), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.out);
    }
  }
  
  private static void installSecondaryDexes(ClassLoader paramClassLoader, File paramFile, List<File> paramList)
    throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException
  {
    if (!paramList.isEmpty())
    {
      if (Build.VERSION.SDK_INT >= 19) {
        V19.install(paramClassLoader, paramList, paramFile);
      }
    }
    else {
      return;
    }
    if (Build.VERSION.SDK_INT >= 14)
    {
      V14.install(paramClassLoader, paramList, paramFile);
      return;
    }
    V4.install(paramClassLoader, paramList);
  }
  
  private static final class V14
  {
    private static void install(ClassLoader paramClassLoader, List<File> paramList, File paramFile)
      throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException
    {
      Object localObject = DexInstall.findField(paramClassLoader, "pathList").get(paramClassLoader);
      DexInstall.expandFieldArray(localObject, "dexElements", makeDexElements(localObject, new ArrayList(paramList), paramFile));
    }
    
    private static Object[] makeDexElements(Object paramObject, ArrayList<File> paramArrayList, File paramFile)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
      return (Object[])DexInstall.findMethod(paramObject, "makeDexElements", new Class[] { ArrayList.class, File.class }).invoke(paramObject, new Object[] { paramArrayList, paramFile });
    }
  }
  
  private static final class V19
  {
    private static void install(ClassLoader paramClassLoader, List<File> paramList, File paramFile)
      throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException
    {
      Object localObject1 = DexInstall.findField(paramClassLoader, "pathList").get(paramClassLoader);
      ArrayList localArrayList = new ArrayList();
      DexInstall.expandFieldArray(localObject1, "dexElements", makeDexElements(localObject1, new ArrayList(paramList), paramFile, localArrayList));
      Field localField;
      IOException[] arrayOfIOException1;
      if (localArrayList.size() > 0)
      {
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext()) {
          ((IOException)localIterator.next()).printStackTrace(System.out);
        }
        localField = DexInstall.findField(paramClassLoader, "dexElementsSuppressedExceptions");
        arrayOfIOException1 = (IOException[])localField.get(paramClassLoader);
        if (arrayOfIOException1 != null) {
          break label139;
        }
      }
      label139:
      IOException[] arrayOfIOException2;
      for (Object localObject2 = (IOException[])localArrayList.toArray(new IOException[localArrayList.size()]);; localObject2 = arrayOfIOException2)
      {
        localField.set(paramClassLoader, localObject2);
        return;
        arrayOfIOException2 = new IOException[localArrayList.size() + arrayOfIOException1.length];
        localArrayList.toArray(arrayOfIOException2);
        System.arraycopy(arrayOfIOException1, 0, arrayOfIOException2, localArrayList.size(), arrayOfIOException1.length);
      }
    }
    
    private static Object[] makeDexElements(Object paramObject, ArrayList<File> paramArrayList, File paramFile, ArrayList<IOException> paramArrayList1)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
      try
      {
        Method localMethod2 = DexInstall.findMethod(paramObject, "makeDexElements", new Class[] { ArrayList.class, File.class, ArrayList.class });
        localObject = localMethod2;
      }
      catch (Exception localException1)
      {
        for (;;)
        {
          Object localObject = null;
        }
      }
      if (localObject == null) {}
      try
      {
        Method localMethod1 = DexInstall.findMethod(paramObject, "makePathElements", new Class[] { List.class, File.class, List.class });
        localObject = localMethod1;
      }
      catch (Exception localException2)
      {
        for (;;) {}
      }
      return (Object[])((Method)localObject).invoke(paramObject, new Object[] { paramArrayList, paramFile, paramArrayList1 });
    }
  }
  
  private static final class V4
  {
    private static void install(ClassLoader paramClassLoader, List<File> paramList)
      throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException
    {
      int i = paramList.size();
      Field localField = DexInstall.findField(paramClassLoader, "path");
      StringBuilder localStringBuilder = new StringBuilder((String)localField.get(paramClassLoader));
      String[] arrayOfString = new String[i];
      File[] arrayOfFile = new File[i];
      ZipFile[] arrayOfZipFile = new ZipFile[i];
      DexFile[] arrayOfDexFile = new DexFile[i];
      ListIterator localListIterator = paramList.listIterator();
      while (localListIterator.hasNext())
      {
        File localFile = (File)localListIterator.next();
        String str = localFile.getAbsolutePath();
        localStringBuilder.append(':').append(str);
        int j = localListIterator.previousIndex();
        arrayOfString[j] = str;
        arrayOfFile[j] = localFile;
        arrayOfZipFile[j] = new ZipFile(localFile);
        arrayOfDexFile[j] = DexFile.loadDex(str, str + ".dex", 0);
      }
      localField.set(paramClassLoader, localStringBuilder.toString());
      DexInstall.expandFieldArray(paramClassLoader, "mPaths", arrayOfString);
      DexInstall.expandFieldArray(paramClassLoader, "mFiles", arrayOfFile);
      DexInstall.expandFieldArray(paramClassLoader, "mZips", arrayOfZipFile);
      DexInstall.expandFieldArray(paramClassLoader, "mDexs", arrayOfDexFile);
    }
  }
}


/* Location:              D:\project\semiHumanoidRobot\unzip APK tool\cn.ninebot.ninebot_dex2jar.jar!\com\secneo\apkwrapper\DexInstall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
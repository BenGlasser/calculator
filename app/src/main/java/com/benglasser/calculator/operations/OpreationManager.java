package com.benglasser.calculator.operations;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpreationManager
{
  private static final String PLUGIN_PATH = "app/src/main/resources/descriptors";
  private ArrayList<Operation> operations = new ArrayList();

  /**
   * Load plugin descriptors from a directory location and then load plugin classes.
   *
   * @return
   * @throws Exception
   */
  public ArrayList loadPlugins() throws Exception
  {

    File filePath = new File(PLUGIN_PATH);
    File files[] = filePath.listFiles();

    if (files.length < 1)
    {
      log.warn("no plugin descriptors found");
    }
    // Iterate over files in the plugin directory
    for (File file : files)
    {
      if (file.isFile())
      {
        FileInputStream fstream = new FileInputStream(file);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        // read fully qualified class name of plugin from plugin descriptor file
        String fullyQualifiedName = br.readLine();
        in.close();

        // Convert File to a URL
        URI uri = URI.create("file:/" + PLUGIN_PATH);
        URL url = uri.toURL();
        URL[] urls = new URL[] { url };

        // Create a new class loader with the directory
        ClassLoader loader = new URLClassLoader(urls);
        Class cls = loader.loadClass(fullyQualifiedName);

        // add loaded plugin to plugin list
        operations.add((Operation) cls.newInstance());

      }
      else
      {
        // skip folders
        continue;
      }
    }
    return operations;
  }
}

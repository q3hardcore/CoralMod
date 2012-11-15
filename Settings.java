// From SinglePlayerCommands by simo_415
// Used instead of NandoProps

package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Settings extends Properties {

   private static final long serialVersionUID = 1L;
   private File settings;


   public Settings() {}

   public Settings(File f) {
      this(f, true);
   }

   public Settings(File f, boolean load) {
      this.settings = f;
      if(load) {
         this.load(f);
      }

   }

   public void set(String key, boolean value) {
      this.setProperty(key, (new Boolean(value)).toString());
   }

   public boolean getBoolean(String key, boolean base) {
      String value = this.getProperty(key);

      try {
         return value != null && !value.trim().equalsIgnoreCase("")?(new Boolean(value)).booleanValue():base;
      } catch (Exception e) {
         return base;
      }
   }

   public void set(String key, int value) {
      this.setProperty(key, (new Integer(value)).toString());
   }

   public int getInteger(String key, int base) {
      String value = this.getProperty(key);

      try {
         return this.isEmpty(value)?base:(new Integer(value)).intValue();
      } catch (NumberFormatException nfe) {
         return base;
      }
   }

   public void set(String key, char value) {
      this.setProperty(key, (new Character(value)).toString());
   }

   public char getCharacter(String key, char base) {
      String value = this.getProperty(key);

      try {
         return this.isEmpty(value)?base:value.charAt(0);
      } catch (NumberFormatException nfe) {
         return base;
      }
   }

   public void set(String key, double value) {
      this.setProperty(key, (new Double(value)).toString());
   }

   public double getDouble(String key, double base) {
      String value = this.getProperty(key);

      try {
         return this.isEmpty(value)?base:(new Double(value)).doubleValue();
      } catch (NumberFormatException nfe) {
         return base;
      }
   }

   public void set(String key, float value) {
      this.setProperty(key, (new Float(value)).toString());
   }

   public float getFloat(String key, float base) {
      String value = this.getProperty(key);

      try {
         return this.isEmpty(value)?base:(new Float(value)).floatValue();
      } catch (NumberFormatException nfe) {
         return base;
      }
   }

   public void set(String key, String value) {
      this.setProperty(key, value);
   }

   public String getString(String key, String base) {
      String value = this.getProperty(key);
      return this.isEmpty(value)?base:value;
   }

   public boolean save() {
      return this.save("");
   }

   public boolean save(String header) {
      return this.save(this.settings, header);
   }

   public boolean save(File file, String header) {
      if(file != null && !file.isDirectory()) {
         try {
            if(!file.exists()) {
               file.createNewFile();
            }

            FileOutputStream e = new FileOutputStream(file);
            super.store(e, header);
            e.close();
            return true;
         } catch (Exception e) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean load() {
      return this.load(this.settings);
   }

   public boolean load(File file) {
      if(file != null && !file.isDirectory()) {
         try {
            if(!file.exists()) {
               file.createNewFile();
               return true;
            } else {
               super.load(new FileInputStream(file));
               return true;
            }
         } catch (Exception e) {
            return false;
         }
      } else {
         return false;
      }
   }

   public File getFile() {
      return this.settings;
   }

   public void setFile(File settings) {
      this.settings = settings;
   }

   private boolean isEmpty(String value) {
      return value == null || value.trim().equalsIgnoreCase("");
   }
}

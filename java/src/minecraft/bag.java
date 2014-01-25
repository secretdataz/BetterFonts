/*
net/minecraft/client/gui/FontRenderer.java -> bag.java
*/
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import me.isuzutsuki.betterfonts.betterfonts.ConfigParser;
import me.isuzutsuki.betterfonts.betterfonts.StringCache;

import org.lwjgl.opengl.GL11;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;

public class bag
  implements bqq
{
  //BetterFonts start
  public static boolean betterFontsEnabled = true;
  public StringCache stringCache;
  public boolean dropShadowEnabled = true;
  public boolean enabled = true;
  //BetterFonts end
  private static final bqo[] c = new bqo[256];
  private int[] d = new int[256];
  public int a = 9;
  public Random b = new Random();
  private byte[] e = new byte[65536];
  private int[] f = new int[32];
  private final bqo g;
  private final bpx h;
  private float i;
  private float j;
  private boolean k;
  private boolean l;
  private float m;
  private float n;
  private float o;
  private float p;
  private int q;
  private boolean r;
  private boolean s;
  private boolean t;
  private boolean u;
  private boolean v;
  
  public bag(azw paramazw, bqo parambqo, bpx parambpx, boolean paramBoolean)
  {
    this.g = parambqo;
    this.h = parambpx;
    this.k = paramBoolean;
    
    parambpx.a(this.g);
    for (int i1 = 0; i1 < 32; i1++)
    {
      int i2 = (i1 >> 3 & 0x1) * 85;
      int i3 = (i1 >> 2 & 0x1) * 170 + i2;
      int i4 = (i1 >> 1 & 0x1) * 170 + i2;
      int i5 = (i1 >> 0 & 0x1) * 170 + i2;
      if (i1 == 6) {
        i3 += 85;
      }
      if (paramazw.e)
      {
        int i6 = (i3 * 30 + i4 * 59 + i5 * 11) / 100;
        int i7 = (i3 * 30 + i4 * 70) / 100;
        int i8 = (i3 * 30 + i5 * 70) / 100;
        i3 = i6;
        i4 = i7;
        i5 = i8;
      }
      if (i1 >= 16)
      {
        i3 /= 4;
        i4 /= 4;
        i5 /= 4;
      }
      this.f[i1] = ((i3 & 0xFF) << 16 | (i4 & 0xFF) << 8 | i5 & 0xFF);
    }
	//BetterFonts start
    System.out.println("[BetterFonts]Starting BetterFonts...");
    if(parambqo.a().equalsIgnoreCase("textures/font/ascii.png") && this.stringCache == null)
    {
    	this.stringCache = new StringCache(this.f);
 	
   	 /* Read optional config file to override the default font name/size */
   	 ConfigParser config = new ConfigParser();
   	 if(config.loadConfig("/config/BetterFonts.cfg"))
   	 {
   	   String fontName = config.getFontName("SansSerif");
       int fontSize = config.getFontSize(18);
   	   boolean antiAlias = config.getBoolean("font.antialias", false);
   	   dropShadowEnabled = config.getBoolean("font.dropshadow", true);
   	
   	   this.stringCache.setDefaultFont(fontName, fontSize, antiAlias);
   	   System.out.println("BetterFonts configuration loaded");
   	 }
    }
	//BeterFonts end
    d();
  }
  
  public void a(bqp parambqp)
  {
    c();
  }
  
  private void c()
  {
      BufferedImage var1;

      try
      {
    	  var1 = ImageIO.read(azd.A().O().a(this.g).b());
      }
      catch (IOException var17)
      {
          throw new RuntimeException(var17);
      }

      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      int[] var4 = new int[var2 * var3];
      var1.getRGB(0, 0, var2, var3, var4, 0, var2);
      int var5 = var3 / 16;
      int var6 = var2 / 16;
      byte var7 = 1;
      float var8 = 8.0F / (float)var6;
      int var9 = 0;

      while (var9 < 256)
      {
          int var10 = var9 % 16;
          int var11 = var9 / 16;

          if (var9 == 32)
          {
              this.d[var9] = 3 + var7;
          }

          int var12 = var6 - 1;

          while (true)
          {
              if (var12 >= 0)
              {
                  int var13 = var10 * var6 + var12;
                  boolean var14 = true;

                  for (int var15 = 0; var15 < var5 && var14; ++var15)
                  {
                      int var16 = (var11 * var6 + var15) * var2;

                      if ((var4[var13 + var16] >> 24 & 255) != 0)
                      {
                          var14 = false;
                      }
                  }

                  if (var14)
                  {
                      --var12;
                      continue;
                  }
              }

              ++var12;
              this.d[var9] = (int)(0.5D + (double)((float)var12 * var8)) + var7;
              ++var9;
              break;
          }
      }
  }
  private void d()
  {
    try
    {
      InputStream localInputStream = azd.A().O().a(new bqo("font/glyph_sizes.bin")).b();
      localInputStream.read(this.e);
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }
  
  private float a(int paramInt, char paramChar, boolean paramBoolean)
  {
    if (paramChar == ' ') {
      return 4.0F;
    }
    if (("".indexOf(paramChar) != -1) && (!this.k)) {
      return a(paramInt, paramBoolean);
    }
    return a(paramChar, paramBoolean);
  }
  
  private float a(int paramInt, boolean paramBoolean)
  {
    float f1 = paramInt % 16 * 8;
    float f2 = paramInt / 16 * 8;
    float f3 = paramBoolean ? 1.0F : 0.0F;
    
    this.h.a(this.g);
    
    float f4 = this.d[paramInt] - 0.01F;
    
    GL11.glBegin(5);
    GL11.glTexCoord2f(f1 / 128.0F, f2 / 128.0F);
    GL11.glVertex3f(this.i + f3, this.j, 0.0F);
    GL11.glTexCoord2f(f1 / 128.0F, (f2 + 7.99F) / 128.0F);
    GL11.glVertex3f(this.i - f3, this.j + 7.99F, 0.0F);
    GL11.glTexCoord2f((f1 + f4 - 1.0F) / 128.0F, f2 / 128.0F);
    GL11.glVertex3f(this.i + f4 - 1.0F + f3, this.j, 0.0F);
    GL11.glTexCoord2f((f1 + f4 - 1.0F) / 128.0F, (f2 + 7.99F) / 128.0F);
    GL11.glVertex3f(this.i + f4 - 1.0F - f3, this.j + 7.99F, 0.0F);
    GL11.glEnd();
    
    return this.d[paramInt];
  }
  
  private bqo a(int paramInt)
  {
    if (c[paramInt] == null) {
      c[paramInt] = new bqo(String.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(paramInt) }));
    }
    return c[paramInt];
  }
  
  private void b(int paramInt)
  {
    this.h.a(a(paramInt));
  }
  
  private float a(char paramChar, boolean paramBoolean)
  {
    if (this.e[paramChar] == 0) {
      return 0.0F;
    }
    int i1 = paramChar / 'Ā';
    
    b(i1);
    

    int i2 = this.e[paramChar] >>> 4;
    
    int i3 = this.e[paramChar] & 0xF;
    
    float f1 = i2;
    float f2 = i3 + 1;
    
    float f3 = paramChar % '\020' * 16 + f1;
    float f4 = (paramChar & 0xFF) / '\020' * 16;
    float f5 = f2 - f1 - 0.02F;
    float f6 = paramBoolean ? 1.0F : 0.0F;
    
    GL11.glBegin(5);
    GL11.glTexCoord2f(f3 / 256.0F, f4 / 256.0F);
    GL11.glVertex3f(this.i + f6, this.j, 0.0F);
    GL11.glTexCoord2f(f3 / 256.0F, (f4 + 15.98F) / 256.0F);
    GL11.glVertex3f(this.i - f6, this.j + 7.99F, 0.0F);
    GL11.glTexCoord2f((f3 + f5) / 256.0F, f4 / 256.0F);
    GL11.glVertex3f(this.i + f5 / 2.0F + f6, this.j, 0.0F);
    GL11.glTexCoord2f((f3 + f5) / 256.0F, (f4 + 15.98F) / 256.0F);
    GL11.glVertex3f(this.i + f5 / 2.0F - f6, this.j + 7.99F, 0.0F);
    GL11.glEnd();
    
    return (f2 - f1) / 2.0F + 1.0F;
  }
  
  public int a(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    return a(paramString, paramInt1, paramInt2, paramInt3, true);
  }
  
  public int b(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    return a(paramString, paramInt1, paramInt2, paramInt3, false);
  }
  
  public int a(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    GL11.glEnable(3008);
    e();
    int i1;
	//BetterFonts start
    if (paramBoolean && this.dropShadowEnabled)
    {
      i1 = b(paramString, paramInt1 + 1, paramInt2 + 1, paramInt3, true);
      i1 = Math.max(i1, b(paramString, paramInt1, paramInt2, paramInt3, false));
    }
    else
    {
      i1 = b(paramString, paramInt1, paramInt2, paramInt3, false);
    }
	//BetterFonts end
    return i1;
  }
  
  private String b(String paramString)
  {
	//BetterFonts start
  	if (this.betterFontsEnabled && this.stringCache != null)
  	{
  		return paramString;
  	}
	//BetterFonts end
    try
    {
      Bidi localBidi = new Bidi(new ArabicShaping(8).shape(paramString), 127);
      localBidi.setReorderingMode(0);
      return localBidi.writeReordered(2);
    }
    catch (ArabicShapingException localArabicShapingException) {}
    return paramString;
  }
  
  private void e()
  {
    this.r = false;
    this.s = false;
    this.t = false;
    this.u = false;
    this.v = false;
  }
  
  private void a(String paramString, boolean paramBoolean)
  {
    for (int i1 = 0; i1 < paramString.length(); i1++)
    {
      char c1 = paramString.charAt(i1);
      int i2;
      int i3;
      if ((c1 == '§') && (i1 + 1 < paramString.length()))
      {
        i2 = "0123456789abcdefklmnor".indexOf(paramString.toLowerCase().charAt(i1 + 1));
        if (i2 < 16)
        {
          this.r = false;
          this.s = false;
          this.v = false;
          this.u = false;
          this.t = false;
          if ((i2 < 0) || (i2 > 15)) {
            i2 = 15;
          }
          if (paramBoolean) {
            i2 += 16;
          }
          i3 = this.f[i2];
          this.q = i3;
          GL11.glColor4f((i3 >> 16) / 255.0F, (i3 >> 8 & 0xFF) / 255.0F, (i3 & 0xFF) / 255.0F, this.p);
        }
        else if (i2 == 16)
        {
          this.r = true;
        }
        else if (i2 == 17)
        {
          this.s = true;
        }
        else if (i2 == 18)
        {
          this.v = true;
        }
        else if (i2 == 19)
        {
          this.u = true;
        }
        else if (i2 == 20)
        {
          this.t = true;
        }
        else if (i2 == 21)
        {
          this.r = false;
          this.s = false;
          this.v = false;
          this.u = false;
          this.t = false;
          
          GL11.glColor4f(this.m, this.n, this.o, this.p);
        }
        i1++;
      }
      else
      {
        i2 = "".indexOf(c1);
        if ((this.r) && (i2 != -1))
        {
          do
          {
            i3 = this.b.nextInt(this.d.length);
          } while (this.d[i2] != this.d[i3]);
          i2 = i3;
        }
        float f1 = this.k ? 0.5F : 1.0F;
        int i4 = ((c1 == 0) || (i2 == -1) || (this.k)) && (paramBoolean) ? 1 : 0;
        if (i4 != 0)
        {
          this.i -= f1;
          this.j -= f1;
        }
        float f2 = a(i2, c1, this.t);
        if (i4 != 0)
        {
          this.i += f1;
          this.j += f1;
        }
        if (this.s)
        {
          this.i += f1;
          if (i4 != 0)
          {
            this.i -= f1;
            this.j -= f1;
          }
          a(i2, c1, this.t);
          this.i -= f1;
          if (i4 != 0)
          {
            this.i += f1;
            this.j += f1;
          }
          f2 += 1.0F;
        }
        blz localblz;
        if (this.v)
        {
          localblz = blz.a;
          GL11.glDisable(3553);
          localblz.b();
          localblz.a(this.i, this.j + this.a / 2, 0.0D);
          localblz.a(this.i + f2, this.j + this.a / 2, 0.0D);
          localblz.a(this.i + f2, this.j + this.a / 2 - 1.0F, 0.0D);
          localblz.a(this.i, this.j + this.a / 2 - 1.0F, 0.0D);
          localblz.a();
          GL11.glEnable(3553);
        }
        if (this.u)
        {
          localblz = blz.a;
          GL11.glDisable(3553);
          localblz.b();
          int i5 = this.u ? -1 : 0;
          localblz.a(this.i + i5, this.j + this.a, 0.0D);
          localblz.a(this.i + f2, this.j + this.a, 0.0D);
          localblz.a(this.i + f2, this.j + this.a - 1.0F, 0.0D);
          localblz.a(this.i + i5, this.j + this.a - 1.0F, 0.0D);
          localblz.a();
          GL11.glEnable(3553);
        }
        this.i += (int)f2;
      }
    }
  }
  
  private int a(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    if (this.l)
    {
      int i1 = a(b(paramString));
      paramInt1 = paramInt1 + paramInt3 - i1;
    }
    return b(paramString, paramInt1, paramInt2, paramInt4, paramBoolean);
  }
  
  private int b(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (paramString == null) {
      return 0;
    }
    if (this.l) {
      paramString = b(paramString);
    }
    if ((paramInt3 & 0xFC000000) == 0) {
      paramInt3 |= 0xFF000000;
    }
    if (paramBoolean) {
      paramInt3 = (paramInt3 & 0xFCFCFC) >> 2 | paramInt3 & 0xFF000000;
    }
    this.m = ((paramInt3 >> 16 & 0xFF) / 255.0F);
    this.n = ((paramInt3 >> 8 & 0xFF) / 255.0F);
    this.o = ((paramInt3 & 0xFF) / 255.0F);
    this.p = ((paramInt3 >> 24 & 0xFF) / 255.0F);
    
    GL11.glColor4f(this.m, this.n, this.o, this.p);
    
    this.i = paramInt1;
    this.j = paramInt2;
	//BetterFonts start
    if(this.betterFontsEnabled && this.stringCache != null)
    {
    	this.i += stringCache.renderString(paramString, paramInt1, paramInt2, paramInt3, paramBoolean);
    }
    else
    {
    	a(paramString, paramBoolean);
    }
	//BetterFonts end
    
    return (int)this.i;
  }
  
  public int a(String paramString)
  {
	//BetterFonts start
  	if (this.betterFontsEnabled && this.stringCache != null)
  	{
  		return this.stringCache.getStringWidth(paramString);
  	}
	//BetterFonts end
    if (paramString == null) {
      return 0;
    }
    int i1 = 0;
    int i2 = 0;
    for (int i3 = 0; i3 < paramString.length(); i3++)
    {
      char c1 = paramString.charAt(i3);
      
      int i4 = a(c1);
      if ((i4 < 0) && (i3 < paramString.length() - 1))
      {
        c1 = paramString.charAt(++i3);
        if ((c1 == 'l') || (c1 == 'L')) {
          i2 = 1;
        } else if ((c1 == 'r') || (c1 == 'R')) {
          i2 = 0;
        }
        i4 = 0;
      }
      i1 += i4;
      if (i2 != 0) {
        i1++;
      }
    }
    return i1;
  }
  
  public int a(char paramChar)
  {
    if (paramChar == '§') {
      return -1;
    }
    if (paramChar == ' ') {
      return 4;
    }
    int i1 = "".indexOf(paramChar);
    if ((paramChar > 0) && (i1 != -1) && (!this.k)) {
      return this.d[i1];
    }
    if (this.e[paramChar] != 0)
    {
      int i2 = this.e[paramChar] >>> 4;
      int i3 = this.e[paramChar] & 0xF;
      if (i3 > 7)
      {
        i3 = 15;
        i2 = 0;
      }
      i3++;
      
      return (i3 - i2) / 2 + 1;
    }
    return 0;
  }
  
  public String a(String paramString, int paramInt)
  {
    return a(paramString, paramInt, false);
  }
  
  public String a(String paramString, int paramInt, boolean paramBoolean)
  {
	//BetterFonts start
  	if (this.betterFontsEnabled && this.stringCache != null)
  	{
  		return this.stringCache.trimStringToWidth(paramString, paramInt, paramBoolean);
  	}
	//BetterFonts end
    StringBuilder localStringBuilder = new StringBuilder();
    int i1 = 0;
    int i2 = paramBoolean ? paramString.length() - 1 : 0;
    int i3 = paramBoolean ? -1 : 1;
    int i4 = 0;
    int i5 = 0;
    for (int i6 = i2; (i6 >= 0) && (i6 < paramString.length()) && (i1 < paramInt); i6 += i3)
    {
      char c1 = paramString.charAt(i6);
      int i7 = a(c1);
      if (i4 != 0)
      {
        i4 = 0;
        if ((c1 == 'l') || (c1 == 'L')) {
          i5 = 1;
        } else if ((c1 == 'r') || (c1 == 'R')) {
          i5 = 0;
        }
      }
      else if (i7 < 0)
      {
        i4 = 1;
      }
      else
      {
        i1 += i7;
        if (i5 != 0) {
          i1++;
        }
      }
      if (i1 > paramInt) {
        break;
      }
      if (paramBoolean) {
        localStringBuilder.insert(0, c1);
      } else {
        localStringBuilder.append(c1);
      }
    }
    return localStringBuilder.toString();
  }
  
  private String c(String paramString)
  {
    while ((paramString != null) && (paramString.endsWith("\n"))) {
      paramString = paramString.substring(0, paramString.length() - 1);
    }
    return paramString;
  }
  
  public void a(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    e();
    this.q = paramInt4;
    paramString = c(paramString);
    
    c(paramString, paramInt1, paramInt2, paramInt3, false);
  }
  
  private void c(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    List localList = c(paramString, paramInt3);
    for (Iterator var7 =  localList.iterator(); var7.hasNext(); paramInt2 += this.a)
    {
      String var8 = (String)var7.next(); 
      a(var8, paramInt1, paramInt2, paramInt3, this.q, paramBoolean);
    }
  }
  
  public int b(String paramString, int paramInt)
  {
    return this.a * c(paramString, paramInt).size();
  }
  
  public void a(boolean paramBoolean)
  {
    this.k = paramBoolean;
  }
  
  public boolean a()
  {
    return this.k;
  }
  
  public void b(boolean paramBoolean)
  {
    this.l = paramBoolean;
  }
  
  public List c(String paramString, int paramInt)
  {
    return Arrays.asList(d(paramString, paramInt).split("\n"));
  }
  
  String d(String paramString, int paramInt)
  {
    int i1 = e(paramString, paramInt);
    if (paramString.length() <= i1) {
      return paramString;
    }
    String str1 = paramString.substring(0, i1);
    
    int i2 = paramString.charAt(i1);
    int i3 = (i2 == 32) || (i2 == 10) ? 1 : 0;
    String str2 = d(str1) + paramString.substring(i1 + (i3 != 0 ? 1 : 0));
    
    return str1 + "\n" + d(str2, paramInt);
  }
  
  private int e(String paramString, int paramInt)
  {
	//BetterFonts start
  	if (this.betterFontsEnabled && this.stringCache != null)
  	{
  		return this.stringCache.sizeStringToWidth(paramString, paramInt);
  	}
	//BetterFonts end
    int i1 = paramString.length();
    int i2 = 0;
    int i3 = 0;
    int i4 = -1;
    int i5 = 0;
    for (; i3 < i1; i3++)
    {
      char c1 = paramString.charAt(i3);
      switch (c1)
      {
      case '§': 
        if (i3 < i1 - 1)
        {
          char c2 = paramString.charAt(++i3);
          if ((c2 == 'l') || (c2 == 'L')) {
            i5 = 1;
          } else if ((c2 == 'r') || (c2 == 'R') || (b(c2))) {
            i5 = 0;
          }
        }
        break;
      case '\n': 
        i3--;
        break;
      case ' ': 
        i4 = i3;
      default: 
        i2 += a(c1);
        if (i5 != 0) {
          i2++;
        }
        break;
      }
      if (c1 == '\n')
      {
        i3++;i4 = i3;
      }
      else
      {
        if (i2 > paramInt) {
          break;
        }
      }
    }
    if ((i3 != i1) && (i4 != -1) && (i4 < i3)) {
      return i4;
    }
    return i3;
  }
  
  private static boolean b(char paramChar)
  {
    return ((paramChar >= '0') && (paramChar <= '9')) || ((paramChar >= 'a') && (paramChar <= 'f')) || ((paramChar >= 'A') && (paramChar <= 'F'));
  }
  
  private static boolean c(char paramChar)
  {
    return ((paramChar >= 'k') && (paramChar <= 'o')) || ((paramChar >= 'K') && (paramChar <= 'O')) || (paramChar == 'r') || (paramChar == 'R');
  }
  
  private static String d(String paramString)
  {
    String str = "";
    int i1 = -1;
    int i2 = paramString.length();
    while ((i1 = paramString.indexOf('§', i1 + 1)) != -1) {
      if (i1 < i2 - 1)
      {
        char c1 = paramString.charAt(i1 + 1);
        if (b(c1)) {
          str = "§" + c1;
        } else if (c(c1)) {
          str = str + "§" + c1;
        }
      }
    }
    return str;
  }
  
  public boolean b()
  {
    return this.l;
  }
}
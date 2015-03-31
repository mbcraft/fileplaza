----

Please note that all graphics and icons inside are property of MBCRAFT or licensed to
MBCRAFT and you're not allowed to use it for your own purposes.
If you want to use graphics for your own application purchase a license
from the graphical artists that produced it or create your own.

( Thanks :) )

Kind regards

- Marco Bagnaresi

----

In order to compile the sources you need to create a JavaFX 2.2 project (i
use NetBeans) with JavaSE 7. Put all the sources in the src directory.
Create a 'libs' directory with the following
jars inside :


httpclient : all jar files

	source : http://commons.apache.org/httpclient

xstream : xstream jar + optional xmlpull + optional xpp3_min

	source : http://xstream.codehaus.net

And add the libs to the project. Also add JUnit 4.10 for compiling unit tests.

You also need icons for files (create a 'file' folder inside src/it/mbcraft/fileplaza/graphics/icons/ directory, with the following subfolders :

- 16px
- 24px
- 32px
- 48px
- 96px
- 128px
- 256px

and put inside image files with the following naming scheme :

<file_extension>_<px_size>.png  , eg : xls_64.png for xls file extension, 64 pixel square size

You also need to create a 'misc' directory with miscellaneous icons inside it.
Actually the following icons are used :

Page_Lined_32.png
Page_Green_Grid_32.png
Page_Silver_32.png
Minus_Silver_32.png
Drive_32.png
Arrow_Up_Blue_32.png
Create.png
Modify.png
Red_Cross.png
Task_Done_32.png
Image_Woodenframe_32.png
Delete_Woodenframe_32.png
Cross_Red_32.png

------------------------------------------------------------------------



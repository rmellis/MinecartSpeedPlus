Spigot 1.15 seems to work.

```
javac -cp spigot-api-1.15.2-R0.1-20200624.001023-124.jar src/**/*.java
mkdir build
cp -r src/fi build/
cp src/main/resources/plugin.yml build/
cd build
jar cvf ../MinecartSpeedPlus.jar fi/**/*.class plugin.yml
cd ..
```

Your jar is at `MinecartSpeedPlus.jar`.

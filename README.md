<br/>

<div>Simple implementation of classic Snake game in Java.</div>

<br/>

# 

<div align="center">
  <p>
    <img src="https://imgur.com/hzmdXRK.gif"/>
  </p>
</div>

# 

## Controls
<div>• Space for starting new game</div>
<div>• Arrows for controlling the snake</div>

#

## Configuration
<div>Following values can be configured by user in config.properties file:</div>
<div>• Window height</div>
<div>• Window width</div>
<div>• Unit size (cell size)</div>
<div>• Delay (game tempo)</div>

#

## Compilation
<div>To compile project into standalone .jar file, run these commands in root folder:</div>

```
javac -d bin src/*.java
jar cfm Snake.jar manifest.txt -C bin . icon.png config.properties
```

#


## Credits

<div>• Game icon by <a href="https://www.iconfinder.com/konkapp">KonKapp</a> on Iconfinder</div>
<div>• Game font by <a href="https://www.dafont.com/omegapc777.d6598">OmegaPC777</a> on Dafont</div>
# No Feeding Baby

[中文](./README-zh-tw.md)

## About
This is a fabric mod to prevent from feeding baby animals.

Someday, I was breeding many bees with flowers,
and I noticed that many of the flowers are eaten by baby bees instead of adult bees.
I cannot endure seeing that the precious flowers get eaten by baby bees,
although the flowers are not precious at all in my server.
So, I came up with the idea to write a mod to accomplish this goal.
It is also a chance for me to learn how to write a mod.

## Features

### Basic
When you take food and right-click a baby animals, nothing will happen.
For example, wheats and baby cows, flowers and baby bees, etc.

This mod ensures that you will not waste any food on baby animals.

### Configurable
You can choose which baby animals can be fed by the configuration.

In the configuration, you can tell the mod to use whitelist mode or blacklist mode
and add the identifier (such as `minecraft:cow`) into the list to enable/disable
feeding.

You can press ALT (default) and right-click the baby animal with food
to switch the animal into or from the white/blacklist.

### Client- and Server-side Supports
*The mod is designed for client uses.*
However, it also works in the server side, too.

Baby animals get fed only if both two sides allow:

| Client→<br>↓Server | Not Installed  | Allow | Disallow |
|--------------------|----------------|-------|----------|
| Not Installed      | ✔️ | ✔️ | ❌ |
| Allow              | ✔️ | ✔️ | ❌ |
| Disallow           | ❌\* | ❌\* | ❌ |

\* If feeding is disabled by server-side only,
the player will see a falsy feeding,
which in fact doesn't consume any food.

Server ops (with op level at least 2) can set configuration by commands `/nofeedingbaby`.

## Note
It is likely that I have no much time to improve and maintain this mod,
but if you have any suggestion, feel free to open issues on github.

## Client- and Server-side
**Note: I haven't tested the server-side functionality yet.**

The mod is designed for client uses, but it also works in the server side, too.

Baby animals get fed only if both two sides allow:

| Client→<br>↓Server | Not Installed | Allow    | Disallow |
|--------------------|---------------|----------|----------|
| Not Installed      | Allow         | Allow    | Disallow |
| Allow              | Allow         | Allow    | Disallow |
| Disallow           | Disallow      | Disallow | Disallow |

## Configurable
Now this mod is configurable.

The config file is "no-feeding-baby.yml"
and there are 2 keys: `whitelistMode` and `list`.

- `whitelistMode` (Bool) is to say whether the following "list" is whitelist or not.
	If no, then the list is blacklist.
- `list` (List[str]) specifies all the animals in ID (e.g., `minecraft:cow`) in the white/blacklist.
	Baby animals in the whitelist can be fed, and cannot if they are in the blacklist.
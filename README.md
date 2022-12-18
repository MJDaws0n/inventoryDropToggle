# Keep Inventory must be enabled to start with or it will not work. To do this run the command /gamerule keepInventory true

# Inventory Drop Toggle
This is a basic plugin that allows minecraft server uses to set their own keep inventory rules.

## Commands
/inventorytoggle - toggles keep inventory on or off for the player running it
/it - toggles keep inventory on or off for the player running it

## How so set another players
Go into the Inventorydroptoggle directory and then the Playe Data. Find the uuid of the player using a tool like [MCC UUID](https://mcuuid.net/) to find a players UUID.
Then edit the drop value to True - keep inventory off, or False - keep inventory on. This will only work if the server is offline and you must restart or reload after.

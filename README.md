## About

This is a plugin that allows you to integrate ChatGPT into your server as a form of player assistant. You can configure a personality and your basic server features and players can ask it all sorts of questions. 

## Commands

> `/aisa reload` reloads the plugin and defaults
`aisa.admin` permission required
> 

> `/aisa force_encrypt` forces the plugin to update the encryption manually. only use this if you have to update your API Key. This can technically be done by setting the API Key in config and setting Encrypted to false
`aisa.admin` permission required
> 

> `/ask <question>` This the main command! The plugin will attempt to answer any question that follows and should hopefully ignore anything that isn’t minecraft or server related.
> 

## API KEY SECURITY NOTICE

By default, the plugin is designed to use an encryption algorithm to mask your tokens and orgs. Its not entirely fool proof, and you should monitor your usage closely. If you see that your usage includes models you normally aren’t using - change keys immediately and remove the old one. 

I do not include an encryption class for self compiling users. You will need to implement your own logic here. I would recommend hosting your own webservice and making authenticated calls to that instead of storing them anywhere staff/players might have access to them.

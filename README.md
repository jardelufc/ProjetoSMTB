#Sistema de Monitoramento de Temperatura de Bebidas

Este projeto consiste basicamente em medir a temperatura de bebidas e ao atingir uma certa faixa de temperatura, o usuário poderá saber se a bebida está no ponto. Para isso, utilizamos um sensor de temperatura chamado PT100 que calcula a temperatura de acordo com a resistência que ele está medindo.

Instruções para compilar o projeto:

 1. Baixe o projeto e salve-o onde desejar.
 2. É necessário ter instalado o Attolic TrueSTUDIO baixado no PC. Pois, ao fazer build (CTRL + B) no projeto , ele irá gerar um arquivo com terminação .hex.
 3. Em seguida, depois de gerado o arquivo .hex, é preciso gerar o .dfu que será colocada na blue pill.
 4. Feito isso, a blue pill já estará com o código pronta para funcionar corretamente.
 5. O último passo é fazer a montagem do circuito , seguindo o esquemático que está na pasta Docs.
 
Obs: Caso o projeto esteja com o path dos arquivos diferente do da máquina atual, é necessário consertar o problema manualmente mudando o path nas configurações.

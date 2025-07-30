# MCO2-PVZ

Plants Vs Zombies on Java with GUI

## Directory/Component Descriptions
- **controller/**: Bridges game model and game view while handling all the logic
- **model/**: Contains all the models the game requires
- **model/inventory/**: Inventory management, including bins and inventory manager.
- **view/**: Contains all the listeners and main game view(display)
- **view/assets/**: contains all the image files the program calls
- **view/audio/**: contains all the audio files the program calls
- **Driver.java**: Calls controller class and starts the game

## Compiling and Running the Program
1. Open the Command Prompt and go to the game directory
2. **Type**: javac Driver.java
3. **Type**: java -Dsun.java2d.opengl=false Driver

## Notes
- The -Dsun.java2d.opengl=false command sets a java system property used to control how Java 2D graphics are rendered. This commands offloads 2D rendering to the GPU instead of only relying to the CPU. After testing on different devices we concluded that adding this command helps with the performance and reduces lag.

# Pokémon Floss Matcher
This tool provides users with a list of DMC embroidery
floss colors to stitch their favorite Pokémon.



## Demo
![App screenshot](assets/app-example.PNG)
[Live Link](https://pokemon-floss-matcher-xy2a.onrender.com/)

## How It Works

When a Pokémon is matched, the program first checks for the existence of a cached result. If none exists, it queries
PokéAPI for the Pokémon's image link. That image is saved, and its color palette extracted using the Color Thief 
library. Each color is then converted RGB→XYZ→LAB for comparison to the floss colors.

Finding the closest match to a reference is not as simple as comparing the RGB values and selecting the color with 
the smallest difference in values compared to the original. RGB distance does not translate to the human eye's 
perception. Take the following RGB values: (255, 255, 255) and (255, 127, 255). These differ in a single value by 78, 
but the colors are visually very different (white and light fuchsia). Now take the values (0, 255, 0) and (0, 255, 127). 
These differ in a single value by 127, but are two similar shades of green. 

To compare colors more accurately from the perspective of the human eye, they must be converted to CIELAB color space. 
This is a perceptually uniform color space where numerical differences correspond to equal perceived color differences. 
Colors are then compared using the CIE76 color difference formula, developed in 1976. While more complex formulas have 
since been developed to address CIELAB's imperfections, CIE76 is well suited for this application's purpose.

Upon pairing every extracted color with its closest matching floss, the results are packaged alongside the Pokémon's 
name and image into a match result. This result is both returned to the user and cached locally. The next time this 
Pokémon is searched, the cached result is returned instantly without repeating the entire pipeline.

## Installation
### Locally

### With Docker




## Tech Stack
### Frontend:
- React 19
- TypeScript
- Bootstrap 5
- Vite 7

### Backend:
- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Webflux (WebClient for API calls)
- Maven
- Jackson (JSON serialization)
- Color Thief by Sven Woltmann (color palette extraction)
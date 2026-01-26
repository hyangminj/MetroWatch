#!/usr/bin/env node
/**
 * Generate 512x512 PNG app icon for Play Store using pure Node.js
 * Creates an SVG and provides instructions for conversion
 */

import { writeFileSync } from 'fs';
import { dirname, join } from 'path';
import { fileURLToPath } from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));

// SVG template for the MetroWatch icon
const createSvg = (size) => `<?xml version="1.0" encoding="UTF-8"?>
<svg xmlns="http://www.w3.org/2000/svg" width="${size}" height="${size}" viewBox="0 0 108 108">
  <!-- Background -->
  <rect width="108" height="108" fill="#212121"/>

  <!-- Inner circle gradient effect -->
  <circle cx="54" cy="54" r="40" fill="#2C2C2C"/>

  <!-- Metronome body (triangle) -->
  <polygon points="54,25 35,75 73,75" fill="#FFFFFF"/>

  <!-- Metronome base -->
  <rect x="32" y="75" width="44" height="5" fill="#FFFFFF"/>

  <!-- Pendulum line -->
  <line x1="54" y1="35" x2="54" y2="65" stroke="#1E88E5" stroke-width="2"/>

  <!-- Pendulum weight -->
  <rect x="52" y="63" width="4" height="4" fill="#1E88E5"/>

  <!-- Beat indicator dot (green) -->
  <circle cx="54" cy="30" r="3" fill="#4CAF50"/>
</svg>`;

// Generate SVG files
const svg512 = createSvg(512);
const svgPath = join(__dirname, 'app_icon.svg');

writeFileSync(svgPath, svg512);
console.log(`Created: ${svgPath}`);

console.log(`
==============================================
MetroWatch App Icon Generated!
==============================================

SVG file created: ${svgPath}

To convert to PNG (512x512), use one of these methods:

Method 1: Online Converter (Easiest)
------------------------------------
1. Go to https://cloudconvert.com/svg-to-png
2. Upload app_icon.svg
3. Set output size to 512x512
4. Download the PNG

Method 2: macOS Preview
-----------------------
1. Open app_icon.svg in Preview
2. File → Export
3. Format: PNG
4. Resolution: 512x512
5. Save as app_icon_512.png

Method 3: Browser (Chrome/Firefox)
----------------------------------
1. Open generate_icon_svg.html in browser
2. Right-click the canvas → Save Image As
3. Save as app_icon_512.png

The SVG file can be directly used with most image tools.
==============================================
`);

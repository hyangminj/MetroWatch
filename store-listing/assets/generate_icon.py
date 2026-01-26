#!/usr/bin/env python3
"""
Generate 512x512 PNG app icon for Play Store from vector drawable.
Uses pure Python with cairo bindings.
"""

import cairo
import math

def create_icon(output_path, size=512):
    # Create surface
    surface = cairo.ImageSurface(cairo.FORMAT_ARGB32, size, size)
    ctx = cairo.Context(surface)

    # Scale from 108dp to target size
    scale = size / 108.0
    ctx.scale(scale, scale)

    # Background (dark gray circle area)
    ctx.set_source_rgb(0x21/255, 0x21/255, 0x21/255)  # #212121
    ctx.rectangle(0, 0, 108, 108)
    ctx.fill()

    # Inner circular gradient effect
    ctx.set_source_rgb(0x2C/255, 0x2C/255, 0x2C/255)  # #2C2C2C
    ctx.arc(54, 54, 40, 0, 2 * math.pi)
    ctx.fill()

    # Metronome body (triangle) - white
    ctx.set_source_rgb(1, 1, 1)  # #FFFFFF
    ctx.move_to(54, 25)
    ctx.line_to(35, 75)
    ctx.line_to(73, 75)
    ctx.close_path()
    ctx.fill()

    # Metronome base - white
    ctx.set_source_rgb(1, 1, 1)  # #FFFFFF
    ctx.rectangle(32, 75, 44, 5)  # 76-32=44 width, 80-75=5 height
    ctx.fill()

    # Pendulum - blue
    ctx.set_source_rgb(0x1E/255, 0x88/255, 0xE5/255)  # #1E88E5
    ctx.set_line_width(2)
    ctx.move_to(54, 35)
    ctx.line_to(54, 65)
    ctx.stroke()

    # Pendulum weight
    ctx.set_source_rgb(0x1E/255, 0x88/255, 0xE5/255)  # #1E88E5
    ctx.rectangle(52, 63, 4, 4)
    ctx.fill()

    # Beat indicator dot - green
    ctx.set_source_rgb(0x4C/255, 0xAF/255, 0x50/255)  # #4CAF50
    ctx.arc(54, 30, 3, 0, 2 * math.pi)
    ctx.fill()

    # Save to PNG
    surface.write_to_png(output_path)
    print(f"Created {output_path} ({size}x{size})")

if __name__ == "__main__":
    import os
    script_dir = os.path.dirname(os.path.abspath(__file__))

    # Generate 512x512 for Play Store
    create_icon(os.path.join(script_dir, "app_icon_512.png"), 512)

    # Also generate high-res version
    create_icon(os.path.join(script_dir, "app_icon_1024.png"), 1024)

    print("Done! App icons generated.")

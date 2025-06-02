import pickle
import struct
import pygame
import socket

def send(sock, data):
    if sock._closed:
        return
    data = pickle.dumps(data)
    length = struct.pack('!I', len(data))  # (4 byte)
    sock.sendall(length)                   # 
    sock.sendall(data)  # 
                       
def receive(sock):
    try:
        # length_data = sock.recv(4, socket.MSG_DONTWAIT)
        length_data = sock.recv(4)
        if not length_data:
            return None
        length = struct.unpack('!I', length_data)[0]

        data = b''
        while len(data) < length:
            packet = sock.recv(length - len(data))
            if not packet:
                break
            data += packet

        obj = pickle.loads(data)
        return obj
    except:
        return None

def load_image(image_path,  width=None, height=None):
    """Displays an image at the given position and size using Pygame."""
    # Load the image
    image = pygame.image.load(image_path)
    if width and height:
        image = pygame.transform.scale(image, (width, height))
    return image

    
def display_image(screen, image, x, y):
    # Scale the image to the desired size
    # Blit (draw) the image onto the screen at the specified position
    screen.blit(image, (x, y))


def draw_text(screen, x, y, text, color, bg_color, font, padding=10, draw_box=True):
    text_surface = font.render(text, True, color)
    text_rect = text_surface.get_rect()
    
    # Add padding to the background box
    box_rect = pygame.Rect(x, y, text_rect.width + 2 * padding, text_rect.height + 2 * padding)
    
    # Draw background box
    if draw_box:
        pygame.draw.rect(screen, bg_color, box_rect)
    
    # Blit text onto the screen centered within the box
    screen.blit(text_surface, (x + padding, y + padding))
    return box_rect

def draw_energy(screen, text, energine: int = 0, energine_max: int = 10,
                x: int = 20, y: int = 20, width: int = 15, height: int = 10, 
                spacing: int = 5, color=(0, 0, 0), bg_color=(50, 50, 50), font_size=22):
    
    box = draw_text(screen, x + spacing, y-2*spacing, 
                    text, color, bg_color, 
                    pygame.font.SysFont('', font_size, bold=False), spacing,
                    False )
    x += box.width + spacing
    for i in range(energine):
        rect_x = x + i * (width + spacing)
        rect = pygame.Rect(rect_x, y, width, height)
        pygame.draw.rect(screen, bg_color, rect, border_radius=4)
        inner_rect = rect.inflate(-4, -4)
        pygame.draw.rect(screen, color, inner_rect, border_radius=4)
    
    for i in range(energine, energine_max):
        rect_x = x + i * (width + spacing)
        rect = pygame.Rect(rect_x, y, width, height)
        pygame.draw.rect(screen, bg_color, rect, border_radius=4)
        inner_rect = rect.inflate(-4, -4)
        pygame.draw.rect(screen, (255,255,255), inner_rect, border_radius=4)

    


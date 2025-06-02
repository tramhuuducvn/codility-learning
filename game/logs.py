
from rich import print
from rich.console import Console, inspect
from contextlib import contextmanager
import inspect


console = Console()

def log(msg:str, tag: str=''):
    console.log(f"[bold magenta]{tag}[/bold magenta] {msg}")

def inspect_object(obj, tag: str=''):
    if tag:
        console.log(f"[bold magenta]{tag}[/bold magenta]")
    members = inspect.getmembers(obj)
    for name, value in members:
        if not name.startswith('__'):  # skip dunder methods unless you want them
            console.log(f"{name} = {value}")

@contextmanager
def trylog():
    try:
        yield
    except:
        console.print_exception(show_locals=True)
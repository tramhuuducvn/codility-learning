#!/bin/bash
import subprocess
import time

processes = []

for i in range(1, 20):
    print(f"Run #{i}")
    # Start each process in the background
    p = subprocess.Popen(["python", "client.py", f'{i}'])
    time.sleep(0.1)
    processes.append(p)

# Wait for all processes to finish
for p in processes:
    p.wait()


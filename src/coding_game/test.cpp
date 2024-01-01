#include <iostream>
#define repeat(i, n) for (int i = 0; (i) < (n); ++(i))

int main()
{
    int age = 0;
    int simulation_time = 10;

    repeat(i, simulation_time)
    {
        // Your code here, using 'age' and performing the simulation
        std::cout << "Simulation step " << i + 1 << ", Age: " << age << std::endl;

        // Update 'age' or perform other actions within the loop
        age += 1;
    }

    return 0;
}

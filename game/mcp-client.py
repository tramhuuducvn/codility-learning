from pydantic_ai import Agent
from pydantic_ai.mcp import MCPServerHTTP
import asyncio

server = MCPServerHTTP(url='http://localhost:8000/sse')  
agent = Agent(
    'openai:gpt-4o-mini', mcp_servers=[server],
    system_prompt=(
        "You are an assistant that must use available tools to answer questions. "
        "Do not compute or guess answers yourself. Always call the appropriate tool."
    )) 


async def main():
    async with agent.run_mcp_servers():
        result = await agent.run("What is server name?")
        print(result.output)

if __name__ == "__main__":
    asyncio.run(main())

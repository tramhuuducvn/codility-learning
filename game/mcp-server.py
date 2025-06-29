from mcp.server.fastmcp import FastMCP
from pydantic_ai import Agent
import logfire


from dotenv import load_dotenv

load_dotenv()

logfire.configure()
logfire.instrument_pydantic_ai()

server = FastMCP("Write poem", host="0.0.0.0", port=8000)
# server_agent = Agent('openai:gpt-4o')

# Define a tool to add two numbers
@server.tool()
def add(a: int, b: int) -> int:
    """Add two numbers."""
    return a + b

@server.tool()
def get_server_name() -> str:
    """Get server name"""
    return {'server_name':"Tran van Teo", 'ip': '087.765.123'}


if __name__ == "__main__":
    server.run(transport="sse")


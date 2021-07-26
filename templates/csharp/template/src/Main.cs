using System.Text;
using Nitric.Faas;

namespace Function
{
    class Handler : INitricFunction
    {
        public Response Handle(Trigger trigger){
            return trigger.DefaultResponse(
                Encoding.UTF8.GetBytes("Hello World!")
            );
        }
        static void Main(string[] args)
        {
            Faas.Start(new HelloWorld());
        }
    }
}

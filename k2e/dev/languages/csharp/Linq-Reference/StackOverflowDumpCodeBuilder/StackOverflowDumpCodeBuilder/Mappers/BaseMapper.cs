using System;
using System.Collections.Generic;
using System.Xml.Linq;

namespace StackOverflowDumpCodeBuilder
{
    public class BaseMapper
    {
        private List<string> attributes = new List<string>();

        protected void EnumerateAttributes(IEnumerable<XElement> elements)
        {
            foreach (XElement element in elements)
            {
                foreach (XAttribute attribute in element.Attributes())
                {
                    if (!attributes.Contains(attribute.Name.LocalName))
                    {
                        attributes.Add(attribute.Name.LocalName);
                    }
                }    
            }            

            foreach (string name in attributes)
            {
                Console.WriteLine(name);
            }
        }
        
        protected DateTime GetDateAttributeValue(XElement element, string attributeName)
        {
            if (element.Attribute(attributeName) != null)
            {
                return DateTime.Parse(element.Attribute(attributeName).Value);
            }
            return DateTime.MinValue;
        }

        protected string GetStringAttributeValue(XElement element, string attributeName)
        {
            if (element.Attribute(attributeName) != null)
            {
                return element.Attribute(attributeName).Value;
            }
            else
            {
                return "";
            }
        }

        protected int GetIntAttributeValue(XElement element, string attributeName)
        {
            if (element.Attribute(attributeName) != null)
            {
                return Int32.Parse(element.Attribute(attributeName).Value);
            }
            return 0;
        }
    }
}
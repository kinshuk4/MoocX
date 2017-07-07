using System;
using System.Collections.Generic;
using System.Xml.Linq;

namespace StackOverflowDumpCodeBuilder
{
    public class BadgeMapper: BaseMapper
    {
        public IEnumerable<Badge> Map(IEnumerable<XElement> elements)
        {
            var result = new List<Badge>();
            foreach (XElement element in elements)
            {
                var badge = new Badge();
                badge.Name = GetStringAttributeValue(element, "Name");
                badge.Date = GetDateAttributeValue(element, "Date");
                badge.UserId = GetIntAttributeValue(element, "UserId");
                result.Add(badge);
            }
            return result;
        }        
    }
}
#AWS CLI Cheatsheet

## Describe all instances 
    aws ec2 describe-instances

## Describe instance by tag and value
    aws ec2 describe-instances --filter "Name=tag:Name,Values=oxclo06"

## Describe instances and query the output to get InstanceId
    aws ec2 describe-instances --filter "Name=tag:Name,Values=oxclo06" --query "Reservations[*].Instances[0].InstanceId"

https://gist.github.com/anuragkapur/f5147029f103111a0381
